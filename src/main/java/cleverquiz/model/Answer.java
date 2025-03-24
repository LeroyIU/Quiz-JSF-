package cleverquiz.model;

import jakarta.persistence.*;

/**
 * Entity class representing an answer.
 */
@Entity
@Table(name = "Answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answerId;

    private String text;
    private Boolean correctness;

    public Boolean getCorrectness() {
        return correctness;
    }

    public void setCorrectness(Boolean correctness) {
        this.correctness = correctness;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}
