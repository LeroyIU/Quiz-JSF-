package cleverquiz.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entity class representing a user in the system.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String email;
    private String password;
    private Date lastLogin;
    private Integer xp;
    private Boolean darkmode;
    private String lastname;
    private String name;
    private String plz;
    private String ort;
    private String field;
    private String aboutme;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "Language_ID")
    private Language language;

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public Language getLanguage() {
        return language;
    }
}