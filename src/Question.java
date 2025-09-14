public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;
    private int selectedAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = -1;
    }

    // Getters and setters
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public int getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(int selectedAnswer) { this.selectedAnswer = selectedAnswer; }
}

