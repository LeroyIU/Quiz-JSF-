import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import cleverquiz.model.Category;
import cleverquiz.model.Difficulty;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

@ManagedBean
@ViewScoped
public class QuestionBean implements Serializable {
    private String question;
    private List<String> categories;
    private String difficulty;
    private String selectedCategory;
    private List<Category> categoryObjects;
    private List<Answer> answers = new ArrayList<>();
    private List<Answer> rows = new ArrayList<>();

    public QuestionBean() {
        IController controller = new Controller();
        List<cleverquiz.model.Category> tmp = controller.getCategories();
        categories = new ArrayList<>();
        categoryObjects = new ArrayList<>(); // Initialize the list of Category objects
        for (cleverquiz.model.Category c : tmp) {
            categories.add(c.getName());
            categoryObjects.add(c); // Store the actual Category object
        }

        for (int i = 0; i < 4; i++) {
            rows.add(new Answer());
        }
    }

    /**
     * Gets the question text.
     * @return the question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text.
     * @param question the question text to set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the category of the question.
     * @return the category of the question.
     */
    public String getCategory() {
        return selectedCategory;
    }

    /**
     * Sets the category of the question.
     * @param category the category to set.
     */
    public void setCategory(String category) {
        this.selectedCategory = category;
    }

    /**
     * Gets the difficulty level of the question.
     * @return the difficulty level of the question.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level of the question.
     * @param difficulty the difficulty level to set.
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the list of answers.
     * @return the list of answers.
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * Sets the list of answers.
     * @param answers the list of answers to set.
     */
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Gets the list of answer rows.
     * @return the list of answer rows.
     */
    public List<Answer> getRows() {
        return rows;
    }

    /**
     * Sets the list of answer rows.
     * @param rows the list of answer rows to set.
     */
    public void setRows(List<Answer> rows) {
        this.rows = rows;
    }

    /**
     * Adds a new row to the list of answer rows.
     */
    public void addRow() {
        rows.add(new Answer());
    }

    /**
     * Removes the last row from the list of answer rows if there are more than four rows.
     */
    public void removeRow() {
        if (rows.size() > 4) {
            rows.remove(rows.size() - 1);
        }
    }

    /**
     * Saves the question along with its answers to the database.
     * Validates the form before saving and displays appropriate messages.
     */
    public void saveQuestion() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        try {
            if (isFormValid()) {
                answers.addAll(rows);
                IController controller = new Controller();
                List<cleverquiz.model.Answer> tmp = new ArrayList<>();
                for (Answer a : answers) {
                    cleverquiz.model.Answer answer = new cleverquiz.model.Answer();
                    answer.setText(a.getAnswer());
                    answer.setCorrectness(a.isCorrect());
                    tmp.add(answer);
                }
                Category selectedCategoryObject = getCategoryByName(selectedCategory);
                controller.createQuestion(Difficulty.valueOf(this.difficulty), question, tmp, selectedCategoryObject, sessionBean.getUser().getUserId());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("questionSaved.text")));
                resetForm();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidQuestionForm.text")));
            }
        } catch (Exception e) {
            String errorMessage = bundle.getString("error.text") + ": " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), errorMessage));
            e.printStackTrace(); // Optional: Log the error for debugging purposes
        }
    }

    /**
     * Validates the form inputs for the question and its answers.
     * @return true if the form inputs are valid, false otherwise.
     */
    private boolean isFormValid() {
        if (!isValidInput(question) || !isValidInput(selectedCategory) || !isValidInput(difficulty)) {
            return false;
        }
        boolean atLeastOneCorrect = false;
        Set<String> uniqueAnswers = new HashSet<>();
        for (Answer answer : rows) {
            if (!isValidInput(answer.getAnswer())) {
                return false;
            }
            if (!uniqueAnswers.add(answer.getAnswer())) {
                return false; // Duplicate answer found
            }
            if (answer.isCorrect()) {
                atLeastOneCorrect = true;
            }
        }
        return atLeastOneCorrect;
    }

    /**
     * Validates a single input string.
     * @param input the input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return false; // Disallow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"äöüÄÖÜß-]*$"; // Allow German umlauts
        return Pattern.matches(regex, input);
    }

    /**
     * Resets the form fields and initializes the answer rows.
     */
    public void resetForm() {
        question = null;
        selectedCategory = null;
        difficulty = null;
        rows.clear();
        for (int i = 0; i < 4; i++) {
            rows.add(new Answer());
        }
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

    public static class Answer {
        private String answer;
        private boolean correct;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}