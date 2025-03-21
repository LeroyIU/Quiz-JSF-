package cleverquiz.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    @Enumerated(EnumType.STRING) // Oder ORDINAL
    @Column(nullable = false)
    private Difficulty difficulty;
    private Boolean approved;
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "Category")
    private Category category;
    private LocalDate date;
    private String text;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", createdBy=" + createdBy +
                ", difficulty=" + difficulty +
                ", approved=" + approved +
                ", rating=" + rating +
                ", category=" + category +
                ", date=" + date +
                '}';
    }
}
