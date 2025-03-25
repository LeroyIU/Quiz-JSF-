import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import cleverquiz.model.Answer;
import cleverquiz.model.Category;



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
    private String redirectUrl;
    private List<Category> categoryObjects;
    private int correctAnswersCount;
    private List<Boolean> ratings;
    private int points; // Add a field to store total points

    // Initialize categories and other properties
    public GameBean() {

        IController controller = new Controller();
        List<cleverquiz.model.Category> tmp = controller.getCategories();
        categories = new ArrayList<>();
        categoryObjects = new ArrayList<>(); // Initialize the list of Category objects
        for (cleverquiz.model.Category c : tmp) {
            categories.add(c.getName());
            categoryObjects.add(c); // Store the actual Category object
        }
        selectedAnswers = new boolean[4];
        currentQuestionIndex = 0;

        questions = new ArrayList<>();
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

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectUrl = context.getExternalContext().getRequestParameterMap().get("redirectUrl");
        this.redirectUrl = redirectUrl;
    }

    public void startGame() {
        System.out.println("Selected Category: " + selectedCategory);
        System.out.println("Selected QuestionCount: " + questionCount);
        totalTime = 0;
        questionTime = 0;
        currentQuestionIndex = 0;

        IController controller = new Controller();
        Category selectedCategoryObject = getCategoryByName(selectedCategory);
        if (selectedCategoryObject != null) {
            List<cleverquiz.model.Game> tmp2 = controller.startQuiz(selectedCategoryObject, questionCount);
            for (cleverquiz.model.Game g : tmp2) {
                // To Be Done für Auswertung s Quiz
                Question q = new Question(g.getQuestion().getText(), g.getAnswers());
                questions.add(q);
            }
        } else {
            System.err.println("Error: Selected category not found!");
        }

        ratings = new ArrayList<>(Collections.nCopies(questionCount, null)); // Initialize ratings with null

        loadNextQuestion();
    }

    public void nextQuestion() {
        long currentTime = System.currentTimeMillis();
        questionTime = (currentTime - questionStartTime) / 1000;
        totalTime += questionTime; // Add the time for the current question to the total time
        checkCorrectAnswers(); // Check if the answer is correct
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

    // To be Done Michael
    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            currentQuestion = questions.get(currentQuestionIndex);
            printCurrentQuestionAnswers();
        } else {
            currentQuestion = new Question("No more questions", Arrays.asList(null, null, null, null));
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
        private List<Answer> answers;

        public Question(String text, List<Answer> answers) {
            this.text = text;
            this.answers = answers;
        }

        public String getText() {
            return text;
        }

        public List<Answer> getAnswers() {
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

    public String getNextButtonLabel() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (!isAnyAnswerSelected()) {
            return bundle.getString("pleaseSelectAnswer.text");
        }
        if (currentQuestionIndex + 1 == questionCount) {
            return bundle.getString("finishGame.text");
        }
        return bundle.getString("next.text");
    }

    public Category getCategoryByName(String name) {
        for (Category category : categoryObjects) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // Return null if no matching category is found
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public int getPoints() {
        return points; // Getter for points
    }

    private boolean isAnswerCorrect() {
        if (currentQuestion == null || currentQuestion.getAnswers() == null) {
            return false; // No question or answers to compare
        }

        List<Answer> answers = currentQuestion.getAnswers();
        if (answers.size() != selectedAnswers.length) {
            return false; // Mismatch in size
        }

        for (int i = 0; i < selectedAnswers.length; i++) {
            boolean correctness = answers.get(i) != null && answers.get(i).getCorrectness();
            System.out.println("Answer " + (i + 1) + ": Selected = " + selectedAnswers[i] + ", Correct = " + correctness);
            if (selectedAnswers[i] != correctness) {
                return false; // Return false immediately if any mismatch is found
            }
        }

        return true; // All answers match
    }

    private void checkCorrectAnswers() {
        if (isAnswerCorrect()) {
            correctAnswersCount++;
        }
    }

    public List<Boolean> getRatings() {
        return ratings;
    }

    public void setRating(int questionIndex, boolean isThumbsUp) {
        if (questionIndex >= 0 && questionIndex < ratings.size()) {
            ratings.set(questionIndex, isThumbsUp);
            System.out.println("Rating for question " + questionIndex + " set to: " + (isThumbsUp ? "Thumbs Up" : "Thumbs Down"));
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void printCurrentQuestionAnswers() {
        if (currentQuestion != null && currentQuestion.getAnswers() != null) {
            System.out.println("Answers for the current question:");
            for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
                Answer answer = currentQuestion.getAnswers().get(i);
                System.out.println("Answer " + (i + 1) + ": " + (answer != null ? answer.getText() : "null"));
            }
        } else {
            System.out.println("No current question or answers available.");
        }
    }
}