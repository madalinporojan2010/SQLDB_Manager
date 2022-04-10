package application.dao;

import application.bll.ProductBLL;
import application.connection.ConnectionFactory;
import application.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * An Order Data Access Object that extends the AbstractDAO generic class.
 */
public class OrderDAO extends AbstractDAO<Order> {
    /**
     * Operations on the Order table. <p>
     * DELETE - delete operation (increasing product stock) <p>
     * INSERT - insert operation (decreasing product stock) <p>
     * UPDATE - update operation (increasing/decreasing product stock)
     */
    private enum Operation {
        DELETE, INSERT, UPDATE
    }


    /**
     * Creates an update query for the product model, changing the product stock.
     *
     * @return The update query.
     */
    private String createUpdateQuery() {
        StringBuilder updateQueryString = new StringBuilder();
        updateQueryString.append(" UPDATE ");
        updateQueryString.append(" werehousebd.product");
        updateQueryString.append(" SET ");
        updateQueryString.append(" stock =? ");
        updateQueryString.append(" WHERE ");
        updateQueryString.append(" (`idProduct` =? )");
        return updateQueryString.toString();
    }

    /**
     * Updates the product stock at the Order.idProduct in the Product table.
     *
     * @param id           The order id.
     * @param operation    The operation.
     * @param updatedOrder The updated order (when operation = UPDATE).
     */
    public void updateStock(int id, Operation operation, Order updatedOrder) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            if (operation == Operation.DELETE) {
                statement.setInt(1, new ProductDAO().findById(this.findById(id).getIdProduct()).getStock() + this.findById(id).getAmount());
            } else if (operation == Operation.INSERT) {
                statement.setInt(1, new ProductDAO().findById(this.findById(id).getIdProduct()).getStock() - this.findById(id).getAmount());
            } else if (operation == Operation.UPDATE) {
                statement.setInt(1, new ProductDAO().findById(this.findById(id).getIdProduct()).getStock() + (this.findById(id).getAmount() - updatedOrder.getAmount()));
            }
            statement.setInt(2, this.findById(id).getIdProduct());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, getClass().getName() + "DAO:update:stock " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Updates the Product stock and then deletes the order.
     *
     * @param id The id where the deletion will take place.
     * @return The superClass deleteById value.
     */
    public boolean deleteById(int id) {
        updateStock(id, Operation.DELETE, null);
        return super.deleteById(id);
    }

    /**
     * Inserts an Order in the Order table. And decreases the respective Product stock.
     *
     * @param order The Order to be inserted.
     * @return The inserted order.
     */
    public Order insert(Order order) {
        if (order.getAmount() <= new ProductBLL().findProductById(order.getIdProduct()).getStock()) {
            Order insertedOrder = super.insert(order);
            updateStock(order.getIdOrder(), Operation.INSERT, null);
            return insertedOrder;
        }
        return null;
    }

    /**
     * Updates an Order in the Order table and increases/decreases the respective Product stock.
     *
     * @param order The order to be updated.
     * @return The updated order.
     */
    public Order update(Order order) {
        int amountDiff = this.findById(order.getIdOrder()).getAmount() - order.getAmount();
        if (amountDiff < 0 && Math.abs(amountDiff) > new ProductDAO().findById(order.getIdProduct()).getStock()) {
            return null;
        }
        updateStock(order.getIdOrder(), Operation.UPDATE, order);
        return super.update(order);
    }
}
