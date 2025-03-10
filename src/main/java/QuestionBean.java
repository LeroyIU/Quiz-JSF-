import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (isFormValid()) {
            answers.addAll(rows);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Question saved successfully"));
            // ToDo: Remove print
            printQuestionDetails();

            // Add connector to database

            resetForm();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "All fields are required, answers must be unique, and at least one answer must be marked as correct"));
        }
    }

    private boolean isFormValid() {
        if (question == null || question.isEmpty() || category == null || category.isEmpty() || difficulty == null || difficulty.isEmpty()) {
            return false;
        }
        boolean atLeastOneCorrect = false;
        Set<String> uniqueAnswers = new HashSet<>();
        for (Answer answer : rows) {
            if (answer.getAnswer() == null || answer.getAnswer().isEmpty()) {
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