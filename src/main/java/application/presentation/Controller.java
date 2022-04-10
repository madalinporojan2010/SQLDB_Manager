package application.presentation;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * The controller of the Order Management application that links the models with the view.
 */
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


    /**
     * The Controller constructor that instantiates the MainGUI and changes the way it looks. Also, it updates the content of the corresponding tables
     * and adds action listeners to the main gui buttons and input UI.
     *
     * @throws ClassNotFoundException Stating that the classes corresponding to the models were not found.
     */
    public Controller() throws ClassNotFoundException {
        mainGUI = new MainGUI();
        changeGUI();
        updateAllTables();

        addActionListenersToMainGUI();
    }

    /**
     * Updates the Client, Product and Order JTables.
     */
    public void updateAllTables() {
        GUIOperations.updateTableEntries(mainGUI.getClientTableModel(), clientBLL);
        GUIOperations.updateTableEntries(mainGUI.getProductTableModel(), productBLL);
        GUIOperations.updateTableEntries(mainGUI.getOrderTableModel(), orderBLL);
    }

    /**
     * Changes the way the UI looks based on the drop box menu.<p>
     * Client, Product or Order Management.
     */
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

    /**
     * Adds the update action listener to the update button.
     */
    public void addUpdateActionListenerToUpdateButtonFromMainGUI() {
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
        mainGUI.getUpdateButton().addActionListener(updateFromTableButtonListener);
    }

    /**
     * Adds the insert action listener to the insert button.
     */
    public void addInsertTableActionListenerToInsertButtonFromMainGUI() {
        ActionListener insertTableButtonListener = e -> {
            if (selectedTable.contains("Client")) {
                insertIntoClientGUI = new InsertIntoClientGUI();
                addActionListenersToInsertToTable(clientBLL, clientModel, insertIntoClientGUI.getTextFields(), insertIntoClientGUI.getFrame(), insertIntoClientGUI.getExecuteButton());
            } else if (selectedTable.contains("Product")) {
                insertIntoProductGUI = new InsertIntoProductGUI();
                addActionListenersToInsertToTable(productBLL, productModel, insertIntoProductGUI.getTextFields(), insertIntoProductGUI.getFrame(), insertIntoProductGUI.getExecuteButton());
            }
        };
        mainGUI.getInsertButton().addActionListener(insertTableButtonListener);
    }

    /**
     * Adds the delete action listener to the delete button.
     */
    public void addDeleteFromTableActionListenerToDeleteButtonFromMainGUI() {
        ActionListener deleteFromTableButtonListener = e -> {
            if (selectedTable.contains("Client")) {
                GUIOperations.deleteObject(clientBLL, mainGUI.getClientTable());
            } else if (selectedTable.contains("Product")) {
                GUIOperations.deleteObject(productBLL, mainGUI.getProductTable());
            } else if (selectedTable.contains("Order")) {
                GUIOperations.deleteObject(orderBLL, mainGUI.getOrderTable());
            }
            updateAllTables();
        };
        mainGUI.getDeleteButton().addActionListener(deleteFromTableButtonListener);
    }

    /**
     * Adds the action listeners to the main gui buttons and input UI.
     */
    public void addActionListenersToMainGUI() {
        ItemListener tableBoxListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getSource() instanceof JComboBox) {
                    selectedTable = ((JComboBox<?>) e.getSource()).getSelectedItem().toString();
                    changeGUI();
                }
            }
        };

        ActionListener orderButtonListener = e -> {
            if (selectedTable.contains("Order")) {
                GUIOperations.makeOrder(mainGUI.getTables());
            }
            updateAllTables();
        };
        ActionListener printBillButtonListener = e -> {
            if (selectedTable.contains("Order")) {
                try {
                    PrintBill.printBill();
                    JOptionPane.showMessageDialog(null, "Bill printed succesfully!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } catch (NoSuchElementException | FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "The are no orders!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }
        };
        addInsertTableActionListenerToInsertButtonFromMainGUI();
        addUpdateActionListenerToUpdateButtonFromMainGUI();
        addDeleteFromTableActionListenerToDeleteButtonFromMainGUI();
        mainGUI.getTableBox().addItemListener(tableBoxListener);
        mainGUI.getOrderButton().addActionListener(orderButtonListener);
        mainGUI.getPrintBillButton().addActionListener(printBillButtonListener);
    }

    /**
     * Adds the execute action listeners for inserting, to the class specific execute button.
     *
     * @param classBLL   Generic class that corresponds to the Business Layer.
     * @param classModel Generic class that corresponds to the model.
     * @param textFields The Text fields corresponding to the respective generic class.
     * @param frame      The frame corresponding to the respective generic class.
     * @param button     The button corresponding to the insert operation.
     */
    public void addActionListenersToInsertToTable(Class classBLL, Class classModel, List<JTextField> textFields, JFrame frame, JButton button) {
        ActionListener executeButtonListener = e -> {
            try {
                GUIOperations.insertObject(classBLL, classModel, textFields);
                updateAllTables();
                frame.dispose();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }
        };
        button.addActionListener(executeButtonListener);
    }

    /**
     * Adds the execute action listeners for updating, to the class specific execute button.
     *
     * @param classBLL   Generic class that corresponds to the Business Layer.
     * @param classModel Generic class that corresponds to the model.
     * @param textFields The Text fields corresponding to the respective generic class.
     * @param frame      The frame corresponding to the respective generic class.
     * @param button     The button corresponding to the update operation.
     */
    public void addActionListenersToUpdateTable(Class classBLL, Class classModel, List<JTextField> textFields, JFrame frame, JButton button) {
        ActionListener executeButtonListener = e -> {
            try {
                GUIOperations.updateObject(classBLL, classModel, textFields);
                updateAllTables();
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "CHECK INPUT!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        };
        button.addActionListener(executeButtonListener);
    }

    /**
     * Updates the fields from the updateGUI required for the update operation.
     *
     * @param table      The table that contains the data to be updated.
     * @param textFields The Text fields corresponding to the respective table.
     */
    public void updateFields(JTable table, List<JTextField> textFields) {
        if (table.getSelectedRow() != -1) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                textFields.get(column).setText(table.getValueAt(table.getSelectedRow(), column).toString());
            }
        }
    }

    /**
     * Runs the application.
     */
    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

