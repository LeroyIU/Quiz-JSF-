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
import cleverquiz.model.User;


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
    private int points;

    /**
     * Initializes the GameBean, setting up categories and other properties.
     */
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

    /**
     * Gets the list of category names.
     * @return List of category names.
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Gets the selected category name.
     * @return The selected category name.
     */
    public String getSelectedCategory() {
        return selectedCategory;
    }

    /**
     * Sets the selected category name.
     * @param selectedCategory The category name to set.
     */
    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    /**
     * Gets the number of questions in the quiz.
     * @return The question count.
     */
    public int getQuestionCount() {
        return questionCount;
    }

    /**
     * Sets the number of questions in the quiz.
     * @param questionCount The question count to set.
     */
    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    /**
     * Gets the total time spent on the quiz.
     * @return Total time in seconds.
     */
    public long getTotalTime() {
        return totalTime; 
    }

    /**
     * Gets the time spent on the current question.
     * @return Question time in seconds.
     */
    public long getQuestionTime() {
        return questionTime;
    }

    /**
     * Gets the current question.
     * @return The current question.
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Gets the selected answers for the current question.
     * @return Array of selected answers.
     */
    public boolean[] getSelectedAnswers() {
        return selectedAnswers;
    }

    /**
     * Gets the index of the current question.
     * @return The current question index.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Gets the redirect URL.
     * @return The redirect URL.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the redirect URL based on the event parameter.
     * @param event The action event triggering the redirect.
     */
    public void setRedirectUrl(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectUrl = context.getExternalContext().getRequestParameterMap().get("redirectUrl");
        this.redirectUrl = redirectUrl;
    }

    /**
     * Starts the quiz game by initializing questions and resetting properties.
     */
    public void startGame() {
        totalTime = 0;
        questionTime = 0;
        questionStartTime = 0;
        currentQuestionIndex = 0;
        selectedAnswers = new boolean[4];
        points = 0;
        correctAnswersCount = 0;

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
        ratings = new ArrayList<>(Collections.nCopies(questionCount, null));
        loadNextQuestion();
    }

    /**
     * Proceeds to the next question or ends the game if all questions are answered.
     */
    public void nextQuestion() {
        long currentTime = System.currentTimeMillis();
        questionTime = (currentTime - questionStartTime) / 1000;
        totalTime += questionTime;
        checkCorrectAnswers();
        currentQuestionIndex++;

        if (currentQuestionIndex < questionCount) {
            loadNextQuestion();
        } else {
            // End the game
            destroyGame();
        }
        // Reset selectedAnswers and ensure the button is disabled
        selectedAnswers = new boolean[4];
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("quizForm:nextButton");
    }

    /**
     * Loads the next question into the current question property.
     */
    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            currentQuestion = questions.get(currentQuestionIndex);
        } else {
            currentQuestion = new Question("No more questions", Arrays.asList(null, null, null, null));
        }
        selectedAnswers = new boolean[4];
        questionStartTime = System.currentTimeMillis();
    }

    /**
     * Checks if the game is over.
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return currentQuestion == null;
    }

    /**
     * Ends the game and updates user data.
     */
    public void destroyGame() {
        currentQuestion = null;

        // Stop polling explicitly
        stopPolling();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();

        // Update user data to add received points
        User user = sessionBean.getUser();
        user.setXp(user.getXp() + points);
        controller.editProfile(user);
    }

    /**
     * Ends the game and redirects to the specified URL.
     */
    public void destroyGameWithRedirect() {
        currentQuestion = null;

        // Stop polling explicitly
        stopPolling();
        
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

    /**
     * Checks if any answer is selected for the current question.
     * @return True if at least one answer is selected, false otherwise.
     */
    public boolean isAnyAnswerSelected() {
        // Check if at least one checkbox is selected
        for (boolean answer : selectedAnswers) {
            if (answer) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the elapsed time for the current question.
     * @return Elapsed time in seconds.
     */
    public long getElapsedQuestionTime() {
        if (currentQuestion == null) {
            return 0; // Return 0 if no question is active
        }
        return (System.currentTimeMillis() - questionStartTime) / 1000;
    }

    /**
     * Formats the total time spent on the quiz as MM:SS.
     * @return Formatted total time.
     */
    public String getFormattedTotalTime() {
        long minutes = totalTime / 60;
        long seconds = totalTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Checks if the game is currently active.
     * @return True if the game is active, false otherwise.
     */
    public boolean isGameActive() {
        return currentQuestion != null; // Game is active if there is a current question
    }

    /**
     * Stops polling for updates in the UI.
     */
    public void stopPolling() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("quizForm:poll");
    }

    /**
     * Gets the label for the "Next" button based on the game state.
     * @return The label for the "Next" button.
     */
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

    /**
     * Finds a category object by its name.
     * @param name The name of the category.
     * @return The matching category object, or null if not found.
     */
    public Category getCategoryByName(String name) {
        for (Category category : categoryObjects) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // Return null if no matching category is found
    }

    /**
     * Gets the count of correct answers.
     * @return The count of correct answers.
     */
    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    /**
     * Gets the total points scored in the quiz.
     * @return The total points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Checks if the selected answers for the current question are correct.
     * @return True if the answers are correct, false otherwise.
     */
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

    /**
     * Checks the correctness of the selected answers and updates the score.
     */
    private void checkCorrectAnswers() {
        if (isAnswerCorrect()) {
            correctAnswersCount++;
            if (currentQuestion != null) {
                cleverquiz.model.Difficulty difficulty = currentQuestion.getAnswers().get(0).getQuestion().getDifficulty();
                points += difficulty.getValue(); // Add points based on difficulty
                System.out.println("Points awarded: " + difficulty.getValue() + ", Total points: " + points);
            }
        }
    }

    /**
     * Gets the list of ratings for the questions.
     * @return List of ratings.
     */
    public List<Boolean> getRatings() {
        return ratings;
    }

    /**
     * Sets the rating for a specific question.
     * @param questionIndex The index of the question.
     * @param isThumbsUp True for thumbs up, false for thumbs down.
     */
    public void setRating(int questionIndex, boolean isThumbsUp) {
        if (questionIndex >= 0 && questionIndex < ratings.size()) {
            ratings.set(questionIndex, isThumbsUp);
            System.out.println("Rating for question " + questionIndex + " set to: " + (isThumbsUp ? "Thumbs Up" : "Thumbs Down"));
        }
    }

    /**
     * Gets the list of questions in the quiz.
     * @return List of questions.
     */
    public List<Question> getQuestions() {
        return questions;
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
}