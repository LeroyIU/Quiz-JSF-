package cleverquiz.model;

/**
 * This helper class associates an answer with the answer of the user
 */
public class UserAnswer {

    Answer answer;
    boolean checked;

    public UserAnswer(Answer answer, boolean checked){
        this.answer = answer;
        this.checked = checked;
    }

    public boolean isCorrect(){
        return answer.getCorrectness() == checked;
    }
}
