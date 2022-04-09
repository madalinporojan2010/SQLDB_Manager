package application.presentation;

import application.bll.OrderBLL;
import application.model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private MainGUI mainGUI;
    private InsertIntoClientGUI insertIntoClientGUI;
    private InsertIntoProductGUI insertIntoProductGUI;

    private UpdateClientGUI updateClientGUI;
    private UpdateProductGUI updateProductGUI;
    private UpdateOrderGUI updateOrderGUI;

    private final Class clientModel = Class.forName("application.model.Client");
    private final Class productModel = Class.forName("application.model.Product");
    private final Class orderModel = Class.forName("application.model.Order");
    private final Class clientBLL = Class.forName("application.bll.ClientBLL");
    private final Class productBLL = Class.forName("application.bll.ProductBLL");
    private final Class orderBLL = Class.forName("application.bll.OrderBLL");
    private String selectedTable = "Client";

    public Controller() throws ClassNotFoundException {
        mainGUI = new MainGUI();
        changeGUI();
        updateAllTables();

        addActionListenersToMainGUI();
    }

    public void updateAllTables() {
        updateTableEntries(mainGUI.getClientTableModel(), clientBLL);
        updateTableEntries(mainGUI.getProductTableModel(), productBLL);
        updateTableEntries(mainGUI.getOrderTableModel(), orderBLL);
    }

    public void changeGUI() {
        mainGUI.getClientTableScrollPane().setVisible(false);
        mainGUI.getClientTableLabel().setVisible(false);
        mainGUI.getProductTableScrollPane().setVisible(false);
        mainGUI.getProductTableLabel().setVisible(false);
        mainGUI.getOrderTableScrollPane().setVisible(false);
        mainGUI.getOrderTableLabel().setVisible(false);
        mainGUI.getInsertButton().setVisible(true);
        mainGUI.getOrderButton().setVisible(false);
        mainGUI.getPrintBillButton().setVisible(false);
        mainGUI.getFrame().setSize(671, 537);
        if (selectedTable.contains("Client")) {
            mainGUI.getClientTableScrollPane().setVisible(true);
            mainGUI.getClientTableLabel().setVisible(true);
        } else if (selectedTable.contains("Product")) {
            mainGUI.getProductTableScrollPane().setVisible(true);
            mainGUI.getProductTableLabel().setVisible(true);
        } else if (selectedTable.contains("Order")) {
            mainGUI.getFrame().setSize(1000, 537);
            mainGUI.getInsertButton().setVisible(false);
            mainGUI.getOrderButton().setVisible(true);
            mainGUI.getPrintBillButton().setVisible(true);
            mainGUI.getClientTableScrollPane().setVisible(true);
            mainGUI.getClientTableLabel().setVisible(true);
            mainGUI.getProductTableScrollPane().setVisible(true);
            mainGUI.getProductTableLabel().setVisible(true);
            mainGUI.getOrderTableScrollPane().setVisible(true);
            mainGUI.getOrderTableLabel().setVisible(true);
        }
    }

    public void updateFields(JTable table, List<JTextField> textFields) {
        if (table.getSelectedRow() != -1) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                textFields.get(column).setText(table.getValueAt(table.getSelectedRow(), column).toString());
            }
        }
    }

    public void addActionListenersToUpdateTable(Class classBLL, Class classModel, List<JTextField> textFields, JFrame frame, JButton button) {
        ActionListener executeButtonListener = e -> {
            try {
                updateObject(classBLL, classModel, textFields);
                updateAllTables();
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        button.addActionListener(executeButtonListener);
    }

    public void addActionListenersToMainGUI() {
        ItemListener tableBoxListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getSource() instanceof JComboBox) {
                    selectedTable = ((JComboBox<?>) e.getSource()).getSelectedItem().toString();
                    changeGUI();
                }
            }
        };
        ActionListener insertTableButtonListener = e -> {
            if (selectedTable.contains("Client")) {
                insertIntoClientGUI = new InsertIntoClientGUI();
                addActionListenersToInsertToTable(clientBLL, clientModel, insertIntoClientGUI.getTextFields(), insertIntoClientGUI.getFrame(), insertIntoClientGUI.getExecuteButton());
            } else if (selectedTable.contains("Product")) {
                insertIntoProductGUI = new InsertIntoProductGUI();
                addActionListenersToInsertToTable(productBLL, productModel, insertIntoProductGUI.getTextFields(), insertIntoProductGUI.getFrame(), insertIntoProductGUI.getExecuteButton());
            }
        };
        ActionListener updateFromTableButtonListener = e -> {
            if (selectedTable.contains("Client")) {
                if (mainGUI.getClientTable().getSelectedRow() != -1) {
                    updateClientGUI = new UpdateClientGUI();
                    updateFields(mainGUI.getClientTable(), updateClientGUI.getTextFields());
                    addActionListenersToUpdateTable(clientBLL, clientModel, updateClientGUI.getTextFields(), updateClientGUI.getFrame(), updateClientGUI.getExecuteButton());
                } else
                    JOptionPane.showMessageDialog(null, "SELECT AN ENTRY!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else if (selectedTable.contains("Product")) {
                if (mainGUI.getProductTable().getSelectedRow() != -1) {
                    updateProductGUI = new UpdateProductGUI();
                    updateFields(mainGUI.getProductTable(), updateProductGUI.getTextFields());
                    addActionListenersToUpdateTable(productBLL, productModel, updateProductGUI.getTextFields(), updateProductGUI.getFrame(), updateProductGUI.getExecuteButton());
                } else
                    JOptionPane.showMessageDialog(null, "SELECT AN ENTRY!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            } else if (selectedTable.contains("Order")) {
                if (mainGUI.getOrderTable().getSelectedRow() != -1) {
                    updateOrderGUI = new UpdateOrderGUI();
                    updateFields(mainGUI.getOrderTable(), updateOrderGUI.getTextFields());
                    addActionListenersToUpdateTable(orderBLL, orderModel, updateOrderGUI.getTextFields(), updateOrderGUI.getFrame(), updateOrderGUI.getExecuteButton());
                } else
                    JOptionPane.showMessageDialog(null, "SELECT AN ENTRY!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
            updateAllTables();
        };
        ActionListener deleteFromTableButtonListener = e -> {
            if (selectedTable.contains("Client")) {
                deleteObject(clientBLL, mainGUI.getClientTable());
            } else if (selectedTable.contains("Product")) {
                deleteObject(productBLL, mainGUI.getProductTable());
            } else if (selectedTable.contains("Order")) {
                deleteObject(orderBLL, mainGUI.getOrderTable());
            }
            updateAllTables();
        };
        ActionListener orderButtonListener = e -> {
            if (selectedTable.contains("Order")) {
                makeOrder(mainGUI.getTables());
            }
            updateAllTables();
        };
        mainGUI.getTableBox().addItemListener(tableBoxListener);
        mainGUI.getInsertButton().addActionListener(insertTableButtonListener);
        mainGUI.getUpdateButton().addActionListener(updateFromTableButtonListener);
        mainGUI.getDeleteButton().addActionListener(deleteFromTableButtonListener);
        mainGUI.getOrderButton().addActionListener(orderButtonListener);
    }

    public void addActionListenersToInsertToTable(Class classBLL, Class classModel, List<JTextField> textFields, JFrame frame, JButton button) {
        ActionListener executeButtonListener = e -> {
            try {
                insertObject(classBLL, classModel, textFields);
                updateAllTables();
                frame.dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        };
        button.addActionListener(executeButtonListener);
    }


    public static void updateObject(Class classBLL, Class classModel, List<JTextField> textFields) throws Exception {
        List<JTextField> textFieldsWithoutId = new ArrayList<>();
        textFieldsWithoutId.addAll(textFields.subList(1, textFields.size()));
        Object object = setObjectProprieties(classModel, textFieldsWithoutId);
        Object classBLLObject = null;
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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void makeOrder(List<JTable> tables) {
        if (tables.get(0).getSelectedRow() == -1 || tables.get(1).getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "SELECT THE `CLIENT` AND THE `PRODUCT`!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Order order = new Order();
        OrderBLL orderBLLObj = new OrderBLL();
        int ammount = 0;
        try {
            String ammountString = JOptionPane.showInputDialog("Ammount:");
            if (ammountString == null) {
                return;
            }
            ammount = Integer.parseInt(ammountString);
            order.setAmmount(ammount);
            order.setIdClient((int) tables.get(0).getModel().getValueAt(tables.get(0).getSelectedRow(), 0));
            order.setIdProduct((int) tables.get(1).getModel().getValueAt(tables.get(1).getSelectedRow(), 0));
            try {
                orderBLLObj.insertOrder(order);
            } catch (NullPointerException nullPointerException) {
                JOptionPane.showMessageDialog(null, "INSUFFICIENT STOCK!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    public static Object setObjectProprieties(Class classModel, List<JTextField> textFields) throws Exception {
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
                        value = Integer.parseInt(textFields.get(textFieldsIterator).getText());
                    } else {
                        value = textFields.get(textFieldsIterator).getText();
                    }// afisarea unui singur table/selectie
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
                        throw new Exception("Format error");
                    }
                }
                isFirstField = false;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
        }

        return object;
    }

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
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

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


    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

