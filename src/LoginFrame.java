import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private HashMap<String, User> users;
    private User currentUser;

    public LoginFrame() {
        initializeUsers();
        setupUI();
    }

    private void initializeUsers() {
        users = new HashMap<>();
        users.put("admin", new User("admin", "admin123", "Administrator", "admin@exam.com"));
        users.put("student1", new User("student1", "pass123", "Om", "john@email.com"));
    }

    private void setupUI() {
        setTitle("Online Examination System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel titleLabel = new JLabel("Online Examination System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                currentUser = users.get(username);
                dispose();
                new MainMenuFrame(currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
