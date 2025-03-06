import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class QuestionBean implements Serializable {

    private List<RowData> rows;

    public QuestionBean() {
        rows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rows.add(new RowData());
        }
    }

    public List<RowData> getRows() {
        return rows;
    }

    public void addRow() {
        rows.add(new RowData());
    }

    public static class RowData {
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