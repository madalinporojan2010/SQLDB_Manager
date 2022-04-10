package application.presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The main GUI class for CRUD and order operations on the tables.
 */
public class MainGUI {
    private JTable clientTable;
    private JTable productTable;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton insertButton;
    private JTable orderTable;
    private JButton printBillButton;
    private JComboBox tableBox;
    private JFrame frame;
    private JPanel panel1;
    private JButton orderButton;
    private JLabel clientTableLabel;
    private JLabel productTableLabel;
    private JLabel orderTableLabel;
    private JScrollPane clientTableScrollPane;
    private JScrollPane productTableScrollPane;
    private JScrollPane orderTableScrollPane;

    private DefaultTableModel clientTableModel;
    private DefaultTableModel productTableModel;
    private DefaultTableModel orderTableModel;

    /**
     * Constructs the window for order and CRUD operations on the tables. It initialises the corresponding data to the JTable models.
     */
    public MainGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Main menu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        clientTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientTable.setModel(clientTableModel);

        productTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable.setModel(productTableModel);

        orderTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable.setModel(orderTableModel);
    }

    /**
     * Tables getter that places all the JTables in a list.
     *
     * @return List of JTables
     */
    public List<JTable> getTables() {
        List<JTable> tables = new ArrayList<>();
        tables.add(clientTable);
        tables.add(productTable);
        tables.add(orderTable);
        return tables;
    }

    public JScrollPane getClientTableScrollPane() {
        return clientTableScrollPane;
    }

    public JScrollPane getProductTableScrollPane() {
        return productTableScrollPane;
    }

    public JScrollPane getOrderTableScrollPane() {
        return orderTableScrollPane;
    }

    public JLabel getClientTableLabel() {
        return clientTableLabel;
    }

    public JLabel getProductTableLabel() {
        return productTableLabel;
    }

    public JLabel getOrderTableLabel() {
        return orderTableLabel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public DefaultTableModel getClientTableModel() {
        return clientTableModel;
    }

    public DefaultTableModel getProductTableModel() {
        return productTableModel;
    }

    public DefaultTableModel getOrderTableModel() {
        return orderTableModel;
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public JTable getOrderTable() {
        return orderTable;
    }

    public JComboBox getTableBox() {
        return tableBox;
    }

    public JButton getPrintBillButton() {
        return printBillButton;
    }
}
