import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
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
    private String redirectUrl;

    // Initialize categories and other properties
    public GameBean() {
        /*IController controller = new Controller();
        List<cleverquiz.model.Category> categories = controller.getCategories();
        for (Category categorie : categories) {
            this.categories.add(categorie.getName());
        }

        */

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
          System.out.println("Question Count set to: " + questionCount); // Debug-Ausgabe
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

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectUrl = context.getExternalContext().getRequestParameterMap().get("redirectUrl");
        System.out.println("setRedirectUrl called with: " + redirectUrl); // Debug statement
        this.redirectUrl = redirectUrl;
        System.out.println("Redirect URL successfully set to: " + this.redirectUrl); // Debug statement
    }

    public void startGame() {
        System.out.println("Selected Category: " + selectedCategory);
        System.out.println("Question Count: " + questionCount);
        totalTime = 0;
        questionTime = 0;
        currentQuestionIndex = 0;
        loadNextQuestion();
    }

    public void nextQuestion() {
        long currentTime = System.currentTimeMillis();
        questionTime = (currentTime - questionStartTime) / 1000;
        totalTime += questionTime; // Add the time for the current question to the total time
        printAnswers();
        currentQuestionIndex++;
        System.out.println("currentQuestionIndex: " + currentQuestionIndex);
        System.out.println("questionCount: " + questionCount); // Debug output

        if (currentQuestionIndex < questionCount) {
            loadNextQuestion();
        } else {
            // End of quiz, show a message
            currentQuestion = null;
            System.out.println("Total time taken: " + getFormattedTotalTime()); // Debug output
        }

        // Reset selectedAnswers and ensure the button is disabled
        selectedAnswers = new boolean[4];
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("quizForm:nextButton");
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

    public boolean isGameOver() {
        return currentQuestion == null;
    }

    public void destroyGame() {
        if (!isGameActive()) {
            System.out.println("Game is already destroyed.");
            return; // Exit if the game is already destroyed
        }

        currentQuestion = null;
        currentQuestionIndex = 0;
        selectedAnswers = new boolean[4];
        totalTime = 0;
        questionTime = 0;
        questionStartTime = 0;
        gameId = null;
        System.out.println("Game destroyed.");

        // Stop polling explicitly
        stopPolling();

        // Redirect to the URL stored in the redirectUrl property
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void destroyGameWithRedirect() {
        System.out.println("Destroying game and redirecting to: " + redirectUrl); // Debug statement

        currentQuestion = null;
        currentQuestionIndex = 0;
        selectedAnswers = new boolean[4];
        totalTime = 0;
        questionTime = 0;
        questionStartTime = 0;

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Redirect URL is null or empty. No redirection performed.");
        }
        gameId = null; // Clear gameId after redirect
    }

    public void checkGameOver() {
        if (isGameOver()) {
            destroyGame();
        }
    }

    public boolean isAnyAnswerSelected() {
        // Check if at least one checkbox is selected
        for (boolean answer : selectedAnswers) {
            if (answer) {
                return true;
            }
        }
        return false;
    }

    public long getElapsedQuestionTime() {
        if (currentQuestion == null) {
            return 0; // Return 0 if no question is active
        }
        return (System.currentTimeMillis() - questionStartTime) / 1000;
    }

    public String getFormattedTotalTime() {
        long minutes = totalTime / 60;
        long seconds = totalTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public boolean isGameActive() {
        return currentQuestion != null; // Game is active if there is a current question
    }

    public void stopPolling() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("quizForm:poll");
        System.out.println("Polling stopped.");
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

    public void proceedWithRedirect(String redirectUrl) {
        System.out.println("proceedWithRedirect called by thread: " + Thread.currentThread().getName());
        System.out.println("Proceeding with redirect. Redirect URL: " + redirectUrl);

        destroyGame(); // Reuse existing logic to reset the game state

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            try {
                if (!FacesContext.getCurrentInstance().getExternalContext().isResponseCommitted()) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
                    FacesContext.getCurrentInstance().responseComplete(); // Mark the response as complete
                    System.out.println("Redirection successful to: " + redirectUrl);
                } else {
                    System.err.println("Response already committed. Redirection skipped.");
                }
            } catch (Exception e) {
                System.err.println("Redirection failed: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("Redirect URL is null or empty. No redirection performed.");
        }
    }
}