package cleverquiz.model;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Entity class representing a question.
 */
@Entity
@Table(name = "Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    private Integer createdBy;
    private Boolean approved;
    private Integer rating;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Difficulty")
    private Difficulty difficulty;
}
