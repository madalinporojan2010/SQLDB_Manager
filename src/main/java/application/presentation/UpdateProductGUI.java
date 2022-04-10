package application.presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI class for updating a Product from the Product table.
 */
public class UpdateProductGUI {
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JButton executeButton;
    private JTextField productIdTextField;

    private JFrame frame;
    private JPanel panel1;

    /**
     * Constructs the window for updating a product in the Product table.
     */
    public UpdateProductGUI() {
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
        textFields.add(productIdTextField);
        textFields.add(nameTextField);
        textFields.add(priceTextField);
        textFields.add(stockTextField);
        return textFields;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JTextField getStockTextField() {
        return stockTextField;
    }

    public JButton getExecuteButton() {
        return executeButton;
    }

    public JTextField getProductIdTextField() {
        return productIdTextField;
    }
}
