package application.presentation;

import application.bll.OrderBLL;
import application.model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for all the database operations that are going to be executed from the UI.
 */
public class GUIOperations {
    /**
     * Updates the object created by the user, from the UI.
     *
     * @param classBLL   Generic class that corresponds to the Business Layer.
     * @param classModel Generic class that corresponds to the model.
     * @param textFields The Text fields corresponding to the respective generic class.
     * @throws Exception Stating that the update operation was unsuccessful.
     */
    public static void updateObject(Class classBLL, Class classModel, List<JTextField> textFields) throws Exception {
        List<JTextField> textFieldsWithoutId = new ArrayList<>();
        textFieldsWithoutId.addAll(textFields.subList(1, textFields.size()));
        Object classBLLObject = null;
        Object object = setObjectProprieties(classModel, textFieldsWithoutId);
        try {
            classBLLObject = classBLL.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Method[] methods = classBLL.getMethods();
        Method update = null;
        for (Method method : methods) {
            if (method.getName().contains("update")) {
                update = method;
                break;
            }
        }

        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.getName().contains("setId" + object.getClass().getSimpleName())) {
                method.invoke(object, Integer.parseInt(textFields.get(0).getText()));
                break;
            }
        }

        try {
            update.invoke(classBLLObject, object);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            throw new Exception("Invalid data!");
        }
    }

    /**
     * Takes the selected rows from Client and Product tables and creates a corresponding Order.
     *
     * @param tables The list containing all the tables from the MainGUI.
     */
    public static void makeOrder(List<JTable> tables) {
        if (tables.get(0).getSelectedRow() == -1 || tables.get(1).getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "SELECT THE `CLIENT` AND THE `PRODUCT`!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Order order = new Order();
        OrderBLL orderBLLObj = new OrderBLL();
        int amount = 0;
        try {
            String amountString = JOptionPane.showInputDialog("Amount:");
            if (amountString == null) {
                return;
            }
            amount = Integer.parseInt(amountString);
            order.setAmount(amount);
            order.setIdClient((int) tables.get(0).getModel().getValueAt(tables.get(0).getSelectedRow(), 0));
            order.setIdProduct((int) tables.get(1).getModel().getValueAt(tables.get(1).getSelectedRow(), 0));
            try {
                orderBLLObj.insertOrder(order);
            } catch (NullPointerException | IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, "RECHECK AMOUNT/STOCK!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Takes the selected row from the respective table and deletes it.
     *
     * @param classBLL Generic class that corresponds to the Business Layer.
     * @param table    The table where deletion will take place.
     */
    public static void deleteObject(Class classBLL, JTable table) {
        Object object = null;
        try {
            object = classBLL.getDeclaredConstructors()[0].newInstance();
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.getName().contains("delete")) {
                    if (table.getSelectedRow() != -1) {
                        method.invoke(object, table.getModel().getValueAt(table.getSelectedRow(), 0));
                    } else {
                        JOptionPane.showMessageDialog(null, "SELECT AN ENTRY!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    break;
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a generic object from the data inserted in the text fields.
     *
     * @param classModel Generic class that corresponds to the model.
     * @param textFields The Text fields corresponding to the respective generic class.
     * @return Returns the created generic object from the corresponding text fields.
     */
    public static Object setObjectProprieties(Class classModel, List<JTextField> textFields) {
        Object object = null;
        try {
            object = classModel.getDeclaredConstructors()[0].newInstance();
            int textFieldsIterator = 0;
            boolean isFirstField = true;
            for (Field field : object.getClass().getDeclaredFields()) {
                if (!isFirstField) {
                    String fieldName = field.getName();
                    Object value = null;
                    if (field.getType().getSimpleName().contains("int")) {
                        try {
                            value = Integer.parseInt(textFields.get(textFieldsIterator).getText());
                        } catch (NumberFormatException numberFormatException) {
                            JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                            return null;
                        }
                    } else {
                        value = textFields.get(textFieldsIterator).getText();
                    }
                    if (value != null && !value.equals("")) {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, classModel);
                        Method method = propertyDescriptor.getWriteMethod();
                        try {
                            method.invoke(object, value);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        textFieldsIterator++;
                    } else {
                        JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                }
                isFirstField = false;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * Inserts a generic object created from the text fields into the corresponding table.
     *
     * @param classBLL   Generic class that corresponds to the Business Layer.
     * @param classModel Generic class that corresponds to the model.
     * @param textFields The Text fields corresponding to the respective generic class.
     * @throws Exception Stating that the object was not inserted successfully.
     */
    public static void insertObject(Class classBLL, Class classModel, List<JTextField> textFields) throws Exception {
        Object object = setObjectProprieties(classModel, textFields);
        Object classBLLObject = null;
        try {
            classBLLObject = classBLL.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Method[] methods = classBLL.getMethods();
        Method insert = null;
        for (Method method : methods) {
            if (method.getName().contains("insert")) {
                insert = method;
                break;
            }
        }

        try {
            insert.invoke(classBLLObject, object);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "CHECK YOUR INFORMATION!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Sets the corresponding table content adding the object given.
     *
     * @param tableModel The table model corresponding to the table where the object is from.
     * @param object Adds the object to the corresponding table model from the UI.
     */
    public static void setTableProprieties(DefaultTableModel tableModel, Object object) {
        Object[] columnBuilder = new Object[object.getClass().getDeclaredFields().length];
        Object[] rowBuilder = new Object[object.getClass().getDeclaredFields().length];
        int fieldIterator = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                rowBuilder[fieldIterator] = value;
                columnBuilder[fieldIterator++] = field.getName();
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        tableModel.setColumnIdentifiers(columnBuilder);
        tableModel.addRow(rowBuilder);
    }

    /**
     * Updates the UI table entries that correspond to the specified class.
     *
     * @param tableModel The table to be updated.
     * @param classBLL The class that corresponds to the table.
     */
    @SuppressWarnings("unchecked")
    public static void updateTableEntries(DefaultTableModel tableModel, Class classBLL) {
        Object classBLLObject = null;
        try {
            classBLLObject = classBLL.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Method[] methods = classBLL.getMethods();
        Method findAll = null;
        for (Method method : methods) {
            if (method.getName().contains("findAll")) {
                findAll = method;
                break;
            }
        }
        List<Object> objects = null;
        try {
            objects = new ArrayList<Object>((List<Object>) findAll.invoke(classBLLObject));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        tableModel.getDataVector().removeAllElements();
        if (objects.size() == 0)
            tableModel.setColumnIdentifiers((Object[]) null);
        else {
            for (Object object : objects) {
                setTableProprieties(tableModel, object);
            }
        }
    }

}
