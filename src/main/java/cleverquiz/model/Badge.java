package cleverquiz.model;

import jakarta.persistence.*;

/**
 * Entity class representing a badge.
 */
@Entity
@Table(name = "Badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeId;

    private String name;
    private String title;

    public Integer getBadgeId() {
        return badgeId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "badgeId=" + badgeId +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
