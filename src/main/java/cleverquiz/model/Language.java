package cleverquiz.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Entity class representing a language.
 */
@Entity
@Table(name = "Language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Integer getLanguageId() {
        return languageId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Language language = (Language) obj;
        return Objects.equals(languageId, language.languageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageId);
    }
}