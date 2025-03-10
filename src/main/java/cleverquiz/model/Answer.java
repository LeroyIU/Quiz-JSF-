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
}
