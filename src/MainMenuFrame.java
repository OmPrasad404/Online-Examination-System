import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private User currentUser;

    public MainMenuFrame(User user) {
        this.currentUser = user;
        setupUI();
    }

    private void setupUI() {
        setTitle("Online Examination System - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFullName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Menu buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton profileButton = new JButton("Update Profile");
        profileButton.addActionListener(e -> {
            new ProfileFrame(currentUser).setVisible(true);
            dispose();
        });

        JButton examButton = new JButton("Start Examination");
        examButton.addActionListener(e -> {
            new ExaminationFrame(currentUser).setVisible(true);
            dispose();
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        buttonPanel.add(profileButton);
        buttonPanel.add(examButton);
        buttonPanel.add(new JLabel()); // Spacer
        buttonPanel.add(logoutButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
}

