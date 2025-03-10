package cleverquiz.model;

import jakarta.persistence.*;

/**
 * Entity class representing a language.
 */
@Entity
@Table(name = "Language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;
    private String name;

    public Integer getLanguageId() {
        return languageId;
    }

    public String getName() {
        return name;
    }
}