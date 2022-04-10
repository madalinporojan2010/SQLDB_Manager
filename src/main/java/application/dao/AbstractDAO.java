package application.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.connection.ConnectionFactory;

import javax.swing.*;

/**
 * Class used to create the database CRUD operations for any db table.
 *
 * @param <T> Generic Class parameter.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    private static int nextIdInTable = 1;

    /**
     * The AbstractDAO class constructor that instantiates the Type of the class and sets the next available id in the table.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        setNextIdInTable();
    }

    /**
     * Sets the next available id in the table.
     */
    public void setNextIdInTable() {
        List<T> objects = findAll();
        int freeID = 0;
        for (T object : objects) {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (freeID != (int) field.get(object)) {
                        nextIdInTable = freeID;
                        return;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
            freeID++;
        }
        nextIdInTable = freeID;
    }

    /**
     * The NextId getter.
     *
     * @return The available id.
     */
    public int getNextIdInTable() {
        return nextIdInTable;
    }

    /**
     * Creates the select query for the generic model. The parameter is given to the WHERE sql statement.
     *
     * @param field The column of the WHERE sql statement.
     * @return The select query.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creates an insert query for the generic model.
     *
     * @return The insert query.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT ");
        sb.append(" INTO ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append("(");
        for (Field field : type.getDeclaredFields()) {
            sb.append(" `" + field.getName() + "`, ");
        }
        sb.setCharAt(sb.length() - 2, ')');
        sb.append(" VALUES (");
        sb.append(" ?, ".repeat(type.getDeclaredFields().length));
        sb.setCharAt(sb.length() - 2, ')');
        return sb.toString();
    }

    /**
     * Creates an update query for the generic model.
     *
     * @return The update query.
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append(" SET ");
        for (Field field : type.getDeclaredFields()) {
            sb.append(" " + field.getName() + " =? ,");
        }
        sb.setCharAt(sb.length() - 1, ' ');
        sb.append(" WHERE( id" + type.getSimpleName() + " = ? )");
        return sb.toString();
    }

    /**
     * Creates a delete query for the generic model.
     *
     * @return The delete query.
     */
    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(" DELETE ");
        sb.append(" FROM ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(" ( `id" + type.getSimpleName() + "` = ? )");
        return sb.toString();
    }

    /**
     * Finds all the entries in the database for this model.
     *
     * @return A List of generic models.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("").substring(0, createSelectQuery("").length() - 10);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Finds the generic model at the given id and returns it.
     *
     * @param id The id to be found in the model.
     * @return A generic object found at the respective id.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id" + this.getClass().getSimpleName().substring(0, this.getClass().getSimpleName().length() - 3));
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates the respective objects from the ResultSet of the query.
     *
     * @param resultSet The database query results.
     * @return A list of generic objects.
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Inserts an object in the respective table.
     *
     * @param t Generic object to be inserted.
     * @return The inserted object.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String insertQuery = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(insertQuery);

            boolean isFirstField = true;
            int i = 1;
            for (Field field : t.getClass().getDeclaredFields()) {
                if (!isFirstField) {
                    field.setAccessible(true);
                    statement.setObject(i, field.get(t));
                    i++;
                } else {
                    isFirstField = false;
                    setNextIdInTable();
                    for (Method method : t.getClass().getDeclaredMethods()) {
                        if (method.getName().contains("setId" + type.getSimpleName())) {
                            method.invoke(t, nextIdInTable);
                            break;
                        }
                    }
                    statement.setInt(i++, nextIdInTable);
                }
            }
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        setNextIdInTable();
        return t;
    }

    /**
     * Updates an object in the respective table.
     *
     * @param t Generic object to be updated.
     * @return The updated object.
     */
    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String updateQuery = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(updateQuery);

            int paramIndex = 1;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (paramIndex == 1) {
                    statement.setObject(t.getClass().getDeclaredFields().length + 1, field.get(t));
                }
                statement.setObject(paramIndex, field.get(t));
                paramIndex++;
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Deleted the object from the respective table, at the given id.
     *
     * @param id The id where the deletion will take place.
     * @return TRUE - if the deletion was successful FALSE - if the deletion could not be made.
     */
    public boolean deleteById(int id) {
        if (findById(id) != null) {
            Connection connection = null;
            PreparedStatement statement = null;
            String deleteQuery = createDeleteQuery();
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(deleteQuery);
                statement.setInt(1, id);
                statement.execute();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "DELETE THE CORRESPONDING ORDER FIRST!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
            } finally {
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
            setNextIdInTable();
            return true;
        } else
            return false;
    }
}