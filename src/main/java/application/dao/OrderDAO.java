package application.dao;

import application.bll.OrderBLL;
import application.bll.ProductBLL;
import application.connection.ConnectionFactory;
import application.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class OrderDAO extends AbstractDAO<Order> {
    private enum Operation {
        DELETE, INSERT
    }

    ;

    private String createUpdateStockQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE ");
        sb.append(" werehousebd.product");
        sb.append(" SET ");
        sb.append(" stock =? ");
        sb.append(" WHERE ");
        sb.append(" (`idProduct` =? )");
        return sb.toString();
    }
    //UPDATE `werehousebd`.`product` SET `stock` = '35' WHERE (`idProduct` = '0') and (`name` = 'ad');

    public void updateStock(int id, Operation operation) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateStockQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            if (operation == Operation.DELETE) {
                statement.setInt(1, new ProductDAO().findById(this.findById(id).getIdProduct()).getStock() + this.findById(id).getAmmount());
            } else if (operation == Operation.INSERT) {
                System.out.println("id="+id);
                statement.setInt(1, new ProductDAO().findById(this.findById(id).getIdProduct()).getStock() - this.findById(id).getAmmount());
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

    public boolean deleteById(int id) {
        updateStock(id, Operation.DELETE);
        return super.deleteById(id);
    }

    public Order insert(Order t) {
        if (t.getAmmount() <= new ProductBLL().findProductById(t.getIdProduct()).getStock()) {
            Order order = super.insert(t);
            updateStock(t.getIdOrder(), Operation.INSERT);
            return order;
        }
        return null;
    }
}
