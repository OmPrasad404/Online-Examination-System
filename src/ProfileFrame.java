import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileFrame extends JFrame {
    private User currentUser;
    private JTextField fullNameField;
    private JTextField emailField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ProfileFrame(User user) {
        this.currentUser = user;
        setupUI();
    }

    private void setupUI() {
        setTitle("Update Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel titleLabel = new JLabel("Update Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Full Name
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Full Name:"), gbc);
        fullNameField = new JTextField(currentUser.getFullName(), 20);
        gbc.gridx = 1; gbc.gridy = 1;
        mainPanel.add(fullNameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(currentUser.getEmail(), 20);
        gbc.gridx = 1; gbc.gridy = 2;
        mainPanel.add(emailField, gbc);

        // Password change section
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);

        JLabel passwordLabel = new JLabel("Change Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        mainPanel.add(passwordLabel, gbc);

        gbc.gridwidth = 1;

        // Current Password
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Current Password:"), gbc);
        currentPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 5;
        mainPanel.add(currentPasswordField, gbc);

        // New Password
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("New Password:"), gbc);
        newPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 6;
        mainPanel.add(newPasswordField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 7;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 7;
        mainPanel.add(confirmPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update Profile");
        updateButton.addActionListener(new UpdateProfileListener());

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            dispose();
            new MainMenuFrame(currentUser).setVisible(true);
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private class UpdateProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update basic profile info
            currentUser.setFullName(fullNameField.getText());
            currentUser.setEmail(emailField.getText());

            // Check if password change is requested
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!currentPassword.isEmpty()) {
                if (!currentPassword.equals(currentUser.getPassword())) {
                    JOptionPane.showMessageDialog(ProfileFrame.this, "Current password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(ProfileFrame.this, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newPassword.length() < 6) {
                    JOptionPane.showMessageDialog(ProfileFrame.this, "New password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                currentUser.setPassword(newPassword);
            }

            JOptionPane.showMessageDialog(ProfileFrame.this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

