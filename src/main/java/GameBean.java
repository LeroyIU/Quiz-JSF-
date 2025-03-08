import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ManagedBean
@ViewScoped
public class GameBean implements Serializable {
    private List<String> categories;
    private String selectedCategory;
    private int questionCount;
    private int currentQuestionIndex;
    private Question currentQuestion;
    private boolean[] selectedAnswers;
    private long totalTime;
    private long questionTime;
    private long questionStartTime;
    private List<Question> questions;
    private String gameId;

    // Initialize categories and other properties
    public GameBean() {
        categories = Arrays.asList("Category 1", "Category 2", "Category 3");
        selectedAnswers = new boolean[4];
        currentQuestionIndex = 0;
        questions = Arrays.asList(
            new Question("Sample Question 1", Arrays.asList("Answer 1.1", "Answer 1.2", "Answer 1.3", "Answer 1.4")),
            new Question("Sample Question 2", Arrays.asList("Answer 2.1", "Answer 2.2", "Answer 2.3", "Answer 2.4")),
            new Question("Sample Question 3", Arrays.asList("Answer 3.1", "Answer 3.2", "Answer 3.3", "Answer 3.4")),
            new Question("Sample Question 4", Arrays.asList("Answer 4.1", "Answer 4.2", "Answer 4.3", "Answer 4.4")),
            new Question("Sample Question 5", Arrays.asList("Answer 5.1", "Answer 5.2", "Answer 5.3", "Answer 5.4"))
        );
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getQuestionTime() {
        return questionTime;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public boolean[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public String getGameId() {
        return gameId;
    }

    public void startGame() {
        totalTime = 0;
        questionTime = 0;
        currentQuestionIndex = 0;
        gameId = UUID.randomUUID().toString();
        loadNextQuestion();
    }

    public void nextQuestion() {
        long currentTime = System.currentTimeMillis();
        questionTime = (currentTime - questionStartTime) / 1000;
        totalTime += questionTime;
        printAnswers();
        currentQuestionIndex++;
        if (currentQuestionIndex < questionCount) {
            loadNextQuestion();
        } else {
            // End of quiz, show a message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Game Over!"));
            currentQuestion = null;
        }
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            currentQuestion = questions.get(currentQuestionIndex);
        } else {
            currentQuestion = new Question("No more questions", Arrays.asList("", "", "", ""));
        }
        selectedAnswers = new boolean[4];
        questionStartTime = System.currentTimeMillis();
    }

    private void printAnswers() {
        System.out.println("Question ID: " + currentQuestionIndex);
        for (int i = 0; i < selectedAnswers.length; i++) {
            System.out.println("Answer ID: " + i + ", Selected: " + selectedAnswers[i]);
        }
        System.out.println("Time for question: " + questionTime + " seconds");
    }

    public static class Question {
        private String text;
        private List<String> answers;

        public Question(String text, List<String> answers) {
            this.text = text;
            this.answers = answers;
        }

        public String getText() {
            return text;
        }

        public List<String> getAnswers() {
            return answers;
        }
    }
}