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

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    private static int nextIdInTable = 1;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        setNextIdInTable();
    }

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

    public int getNextIdInTable() {
        return nextIdInTable;
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

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

    private String createDeleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append(" DELETE ");
        sb.append(" FROM ");
        sb.append(" werehousebd." + type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(" ( `id" + type.getSimpleName() + "` = ? )");
        return sb.toString();
    }

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

    private List<T> createObjects(ResultSet resultSet) {
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
                    for(Method method : t.getClass().getDeclaredMethods()) {
                        if(method.getName().contains("setId" + type.getSimpleName())) {
                            method.invoke(t, nextIdInTable);
                            break;
                        }
                    }
                    statement.setInt(i++, nextIdInTable);
                }
            }
            System.out.println(statement.toString());
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

    public T update(T t) {
        // TODO:
        return t;
    }

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
    //DELETE FROM `werehousebd`.`client` WHERE (`idClient` = '2');

    //SELECT * FROM werehousebd.order JOIN werehousebd.product USING (idProduct);
}