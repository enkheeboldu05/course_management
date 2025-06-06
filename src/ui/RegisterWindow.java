package src.ui;

import javax.swing.*;
import src.db.UserDAO;
import src.model.User;

public class RegisterWindow extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JTextField departmentField;
    private JButton submitButton;
    private UserDAO userDAO = new UserDAO();

    public RegisterWindow() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 100, 160, 25);
        add(emailField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 140, 80, 25);
        add(roleLabel);

        roleCombo = new JComboBox<>(new String[]{"student", "teacher"});
        roleCombo.setBounds(100, 140, 160, 25);
        add(roleCombo);

        JLabel deptLabel = new JLabel("Department: ");
        deptLabel.setBounds(20, 180, 80, 25);
        add(deptLabel);

        departmentField = new JTextField();
        departmentField.setBounds(100,180,160,25);
        add(departmentField);

        submitButton = new JButton("Register");
        submitButton.setBounds(90, 220, 120, 25);
        add(submitButton);

        submitButton.addActionListener(e -> handleRegister());

        setVisible(true);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String role = (String) roleCombo.getSelectedItem();
        String department = departmentField.getText();

        User user = new User(username, password, email, department);
        user.setRole(role);
        user.setDepartment(department);

        if (userDAO.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful. Please login.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.");
        }
}
}