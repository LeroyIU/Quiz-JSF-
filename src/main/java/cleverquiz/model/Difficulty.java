package cleverquiz.model;

/**
 * Entity class representing difficulty levels.
 */
public enum Difficulty {
        Easy(1), Medium(2), Hard(4);

        int points;

        Difficulty(int points) {
            this.points = points;
        }

        public int getValue() { return points;}

}