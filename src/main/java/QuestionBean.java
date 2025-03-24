import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import cleverquiz.model.Difficulty;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

@ManagedBean
@SessionScoped
public class QuestionBean implements Serializable {
    private String question;
    private String category;
    private String difficulty;
    private List<Answer> answers = new ArrayList<>();
    private List<Answer> rows = new ArrayList<>();

    public QuestionBean() {
        for (int i = 0; i < 4; i++) {
            rows.add(new Answer());
        }
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getRows() {
        return rows;
    }

    public void setRows(List<Answer> rows) {
        this.rows = rows;
    }

    public void addRow() {
        rows.add(new Answer());
    }

    public void removeRow() {
        if (rows.size() > 4) {
            rows.remove(rows.size() - 1);
        }
    }

    public void saveQuestion() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (isFormValid()) {
            answers.addAll(rows);
            IController controller = new Controller();
            List <cleverquiz.model.Answer> tmp = new ArrayList<>();
            for (Answer a : answers) {
                cleverquiz.model.Answer answer = new cleverquiz.model.Answer();
                answer.setText(a.getAnswer());
                answer.setCorrectness(a.isCorrect());
                tmp.add(answer);
            }

            controller.createQuestion(Difficulty.valueOf(this.difficulty), question, tmp);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question saved successfully"));
            // ToDo: Remove print
            printQuestionDetails();

            controller.createQuestion(Difficulty.valueOf(this.difficulty), question, tmp);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("questionSaved.text")));
            printQuestionDetails();

            resetForm();
        } else {
            printQuestionDetails();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidQuestionForm.text")));
        }
    }

    private boolean isFormValid() {
        if (!isValidInput(question) || !isValidInput(category) || !isValidInput(difficulty)) {
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

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"-]*$";
        return Pattern.matches(regex, input);
    }

    private void printQuestionDetails() {
        System.out.println("Question: " + question);
        System.out.println("Category: " + category);
        System.out.println("Difficulty: " + difficulty);
        for (Answer answer : rows) {
            System.out.println("Answer: " + answer.getAnswer() + " - Correct: " + answer.isCorrect());
        }
    }

    public void resetForm() {
        question = null;
        category = null;
        difficulty = null;
        rows.clear();
        for (int i = 0; i < 4; i++) {
            rows.add(new Answer());
        }
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