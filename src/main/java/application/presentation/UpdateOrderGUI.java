package application.presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI class for updating an Order from the Order table.
 */
public class UpdateOrderGUI {
    private JTextField clientIdTextField;
    private JTextField productIdTextField;
    private JTextField amountTextField;
    private JButton executeButton;
    private JTextField orderIdTextField;
    private JFrame frame;
    private JPanel panel1;

    /**
     * Constructs the window for updating an order in the Order table.
     */
    public UpdateOrderGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Update");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * TextFields getter that places all the TextFields in a list.
     *
     * @return List of JTextField
     */
    public List<JTextField> getTextFields() {
        ArrayList<JTextField> textFields = new ArrayList<>();
        textFields.add(orderIdTextField);
        textFields.add(clientIdTextField);
        textFields.add(productIdTextField);
        textFields.add(amountTextField);
        return textFields;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getClientIdTextField() {
        return clientIdTextField;
    }

    public JTextField getProductIdTextField() {
        return productIdTextField;
    }

    public JTextField getAmountTextField() {
        return amountTextField;
    }

    public JButton getExecuteButton() {
        return executeButton;
    }

    public JTextField getOrderIdTextField() {
        return orderIdTextField;
    }
}
