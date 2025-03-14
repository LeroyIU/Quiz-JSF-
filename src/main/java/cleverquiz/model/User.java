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

    public void setDarkmode(Boolean darkmode) {
        this.darkmode = darkmode;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}