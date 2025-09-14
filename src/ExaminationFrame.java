import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ExaminationFrame extends JFrame {
    private User currentUser;
    private List<Question> questions;
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JLabel questionNumberLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JLabel timerLabel;
    private Timer examTimer;
    private int timeRemaining; // in seconds
    private JButton prevButton, nextButton, submitButton;

    public ExaminationFrame(User user) {
        this.currentUser = user;
        this.currentQuestionIndex = 0;
        this.timeRemaining = 300; // 5 minutes
        initializeQuestions();
        setupUI();
        startTimer();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
            new String[]{"London", "Berlin", "Paris", "Madrid"}, 2));
        questions.add(new Question("Which programming language is known for 'Write Once, Run Anywhere'?",
            new String[]{"C++", "Java", "Python", "JavaScript"}, 1));
        questions.add(new Question("What does HTML stand for?",
            new String[]{"Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "Hyperlink and Text Markup Language"}, 0));
        questions.add(new Question("Which data structure uses LIFO principle?",
            new String[]{"Queue", "Stack", "Array", "Linked List"}, 1));
        questions.add(new Question("What is the time complexity of binary search?",
            new String[]{"O(n)", "O(log n)", "O(n²)", "O(1)"}, 1));
    }

    private void setupUI() {
        setTitle("Online Examination");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header with timer
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel examTitle = new JLabel("Online Examination");
        examTitle.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel = new JLabel("Time Remaining: 05:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);

        headerPanel.add(examTitle, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Question panel
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionNumberLabel = new JLabel("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 12));
        questionPanel.add(questionNumberLabel, BorderLayout.NORTH);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        // Options panel
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);

            final int optionIndex = i;
            optionButtons[i].addActionListener(e -> {
                questions.get(currentQuestionIndex).setSelectedAnswer(optionIndex);
            });
        }

        questionPanel.add(optionsPanel, BorderLayout.SOUTH);

        // Navigation panel
        JPanel navPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        submitButton = new JButton("Submit Exam");

        prevButton.addActionListener(e -> navigateQuestion(-1));
        nextButton.addActionListener(e -> navigateQuestion(1));
        submitButton.addActionListener(e -> submitExam());

        navPanel.add(prevButton);
        navPanel.add(nextButton);
        navPanel.add(submitButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        mainPanel.add(navPanel, BorderLayout.SOUTH);

        add(mainPanel);

        displayQuestion();
        updateNavigationButtons();

        // Prevent closing without confirmation
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int confirm = JOptionPane.showConfirmDialog(ExaminationFrame.this,
                    "Are you sure you want to close the exam? Your progress will be lost.",
                    "Close Exam", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    examTimer.stop();
                    dispose();
                    new MainMenuFrame(currentUser).setVisible(true);
                }
            }
        });
    }

    private void startTimer() {
        examTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                updateTimerDisplay();

                if (timeRemaining <= 0) {
                    examTimer.stop();
                    JOptionPane.showMessageDialog(ExaminationFrame.this, "Time's up! Exam will be submitted automatically.", "Time Up", JOptionPane.WARNING_MESSAGE);
                    submitExam();
                }
            }
        });
        examTimer.start();
    }

    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));

        if (timeRemaining <= 300) { // Last 5 minutes
            timerLabel.setForeground(Color.RED);
        } else if (timeRemaining <= 600) { // Last 10 minutes
            timerLabel.setForeground(Color.ORANGE);
        }
    }

    private void displayQuestion() {
        Question current = questions.get(currentQuestionIndex);
        questionLabel.setText("<html><body style='width: 500px'>" + current.getQuestionText() + "</body></html>");

        String[] options = current.getOptions();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText((char)('A' + i) + ") " + options[i]);
            optionButtons[i].setSelected(current.getSelectedAnswer() == i);
        }

        // Update question number using the proper reference
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
    }

    private void navigateQuestion(int direction) {
        int newIndex = currentQuestionIndex + direction;
        if (newIndex >= 0 && newIndex < questions.size()) {
            currentQuestionIndex = newIndex;
            displayQuestion();
            updateNavigationButtons();
        }
    }

    private void updateNavigationButtons() {
        prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.size() - 1);
    }

    private void submitExam() {
        examTimer.stop();

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to submit the exam?",
            "Submit Exam", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            calculateResults();
        } else {
            examTimer.start();
        }
    }

    private void calculateResults() {
        int correctAnswers = 0;
        int totalQuestions = questions.size();

        for (Question question : questions) {
            if (question.getSelectedAnswer() == question.getCorrectAnswer()) {
                correctAnswers++;
            }
        }

        double percentage = (double) correctAnswers / totalQuestions * 100;

        String resultMessage = String.format(
            "Exam Completed!\n\nCorrect Answers: %d / %d\nPercentage: %.2f%%\n\nStatus: %s",
            correctAnswers, totalQuestions, percentage,
            percentage >= 60 ? "PASSED" : "FAILED"
        );

        JOptionPane.showMessageDialog(this, resultMessage, "Exam Results", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new MainMenuFrame(currentUser).setVisible(true);
    }
}
