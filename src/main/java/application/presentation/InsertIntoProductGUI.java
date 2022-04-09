package application.presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoProductGUI {
    private JPanel panel1;
    private JButton executeButton;
    private JTextField stockTextField;
    private JTextField nameTextField;
    private JTextField priceTextField;

    private JFrame frame;


    public InsertIntoProductGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Insert");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel1);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public List<JTextField> getTextFields() {
        List<JTextField> textFields = new ArrayList<>();
        textFields.add(nameTextField);
        textFields.add(priceTextField);
        textFields.add(stockTextField);

        return textFields;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getExecuteButton() {
        return executeButton;
    }

    public JTextField getStockTextField() {
        return stockTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getPriceTextField() {
        return priceTextField;
    }

    public JFrame getFrame() {
        return frame;
    }
}
