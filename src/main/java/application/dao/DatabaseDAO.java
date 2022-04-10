package application.dao;

import application.connection.ConnectionFactory;
import application.model.BillData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

/**
 * A Database Data Access Object that extends the AbstractDAO generic class.
 */
public class DatabaseDAO extends AbstractDAO<BillData>{
    private String createJoinQuery() {
        return "SELECT o.idOrder, c.idClient, c.name 'clientName', c.age, c.phone, c.address, c.email, p.idProduct, p.name 'productName', p.price, p.stock, o.ammount FROM werehousebd.client c JOIN werehousebd.order o ON (c.idClient = o.idClient) JOIN werehousebd.product p ON(o.idProduct = p.idProduct) ORDER BY o.idOrder";
    }

    public List<BillData> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createJoinQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, this.getClass().getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
