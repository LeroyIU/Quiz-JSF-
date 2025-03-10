package cleverquiz.model;

import jakarta.persistence.*;

/**
 * Entity class representing difficulty levels.
 */
@Entity
@Table(name = "Difficulty")
public class Difficulty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer difficultyId;

    private String name;

    public Integer getDifficultyId() {
        return difficultyId;
    }

    public String getName() {
        return name;
    }
}