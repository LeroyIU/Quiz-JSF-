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

    private void loadNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            currentQuestion = questions.get(currentQuestionIndex);
        } else {
            currentQuestion = new Question("No more questions", Arrays.asList(null, null, null, null));
        }
        selectedAnswers = new boolean[4];
        questionStartTime = System.currentTimeMillis();
    }

    public boolean isGameOver() {
        return currentQuestion == null;
    }

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

    public void destroyGameWithRedirect() {
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
    }

    public void proceedWithRedirect(String redirectUrl) {
        destroyGame();

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            try {
                if (!FacesContext.getCurrentInstance().getExternalContext().isResponseCommitted()) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
                    FacesContext.getCurrentInstance().responseComplete(); // Mark the response as complete
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
        return points;
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
            if (currentQuestion != null) {
                cleverquiz.model.Difficulty difficulty = currentQuestion.getAnswers().get(0).getQuestion().getDifficulty();
                points += difficulty.getValue(); // Add points based on difficulty
                System.out.println("Points awarded: " + difficulty.getValue() + ", Total points: " + points);
            }
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