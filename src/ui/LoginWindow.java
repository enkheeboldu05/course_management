package src.ui;

import javax.swing.*;
import java.awt.event.*;
import src.db.UserDAO;
import src.model.User;
import src.ui.RegisterWindow;
import src.ui.TeacherDashboard;
import src.ui.StudentDashboard;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private UserDAO userDAO = new UserDAO();

    public LoginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
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

        loginButton = new JButton("Login");
        loginButton.setBounds(30, 100, 100, 25);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 100, 100, 25);
        add(registerButton);

        // === Button Events ===
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> new RegisterWindow());

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = userDAO.loginUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername() + "!");
            dispose(); 
            if ("teacher".equals(user.getRole())) {
                new TeacherDashboard(user);
            } else {
                new StudentDashboard(user);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Login failed!");
        }
    }
}
