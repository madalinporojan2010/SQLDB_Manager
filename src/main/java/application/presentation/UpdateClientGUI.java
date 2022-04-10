package application.presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI class for updating a Client from the Client table.
 */
public class UpdateClientGUI {
    private JButton executeButton;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JLabel clientPhoneLabel;
    private JLabel clientEmailLabel;
    private JTextField nameTextField;
    private JLabel clientNameLabel;
    private JTextField ageTextField;
    private JLabel clientAgeLabel;
    private JLabel clientAddressLabel;
    private JTextField addressTextField;
    private JTextField clientIDTextField;
    private JLabel clientIdLabel;
    private JFrame frame;
    private JPanel panel1;

    /**
     * Constructs the window for updating a product in the Product table.
     */
    public UpdateClientGUI() {
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
        textFields.add(clientIDTextField);
        textFields.add(nameTextField);
        textFields.add(ageTextField);
        textFields.add(phoneTextField);
        textFields.add(addressTextField);
        textFields.add(emailTextField);
        return textFields;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getExecuteButton() {
        return executeButton;
    }

    public JTextField getPhoneTextField() {
        return phoneTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JLabel getClientPhoneLabel() {
        return clientPhoneLabel;
    }

    public JLabel getClientEmailLabel() {
        return clientEmailLabel;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JLabel getClientNameLabel() {
        return clientNameLabel;
    }

    public JTextField getAgeTextField() {
        return ageTextField;
    }

    public JLabel getClientAgeLabel() {
        return clientAgeLabel;
    }

    public JLabel getClientAddressLabel() {
        return clientAddressLabel;
    }

    public JTextField getAddressTextField() {
        return addressTextField;
    }

    public JTextField getClientIDTextField() {
        return clientIDTextField;
    }

    public JLabel getClientIdLabel() {
        return clientIdLabel;
    }
}
