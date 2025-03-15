package cleverquiz.model;

import jakarta.persistence.*;

/**
 * Entity class representing difficulty levels.
 */
@Entity
@Table(name = "Difficulty")
public class Difficulty {

    private enum DifficultyValues {
        Easy(1), Medium(2), Hard(4);

        int points;

        DifficultyValues(int points) {
            this.points = points;
        }

        public int getValue() { return points;}

        public static DifficultyValues getByName(String name) {
            return valueOf(name);
        }

        public static int getPoints(String name) {
            return getByName(name).getValue();
        }
    }
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

    public int getPoints() {
        return DifficultyValues.getPoints(name);
    }
}