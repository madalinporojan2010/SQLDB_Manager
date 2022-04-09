package application.presentation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoClientGUI {
    private JButton executeButton;
    private JTextField phoneTextField;
    private JTextField addressTextField;
    private JTextField emailTextField;
    private JTextField nameTextField;
    private JTextField ageTextField;
    private JLabel clientNameLabel;
    private JLabel clientAgeLabel;
    private JLabel clientPhoneLabel;
    private JLabel clientAddressLabel;
    private JLabel clientEmailLabel;


    private JPanel panel1;
    private JFrame frame;


    public InsertIntoClientGUI() {
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
        textFields.add(ageTextField);
        textFields.add(phoneTextField);
        textFields.add(addressTextField);
        textFields.add(emailTextField);

        return textFields;
    }

    public JButton getExecuteButton() {
        return executeButton;
    }
    public JTextField getPhoneTextField() {
        return phoneTextField;
    }

    public JTextField getAddressTextField() {
        return addressTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }

    public JTextField getAgeTextField() {
        return ageTextField;
    }

    public JLabel getClientNameLabel() {
        return clientNameLabel;
    }

    public JLabel getClientAgeLabel() {
        return clientAgeLabel;
    }

    public JLabel getClientPhoneLabel() {
        return clientPhoneLabel;
    }

    public JLabel getClientAddressLabel() {
        return clientAddressLabel;
    }

    public JLabel getClientEmailLabel() {
        return clientEmailLabel;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JFrame getFrame() {
        return frame;
    }
}
