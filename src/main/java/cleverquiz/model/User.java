package cleverquiz.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

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

    public static User create(String name, String email, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    @Enumerated(EnumType.STRING)  // Speichert als VARCHAR anstatt ENUM
    @Column(name = "role")
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(lastLogin, user.lastLogin) &&
                Objects.equals(xp, user.xp) &&
                Objects.equals(darkmode, user.darkmode) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(name, user.name) &&
                Objects.equals(plz, user.plz) &&
                Objects.equals(ort, user.ort) &&
                Objects.equals(field, user.field) &&
                Objects.equals(aboutme, user.aboutme) &&
                role == user.role &&
                Objects.equals(language, user.language); // Vergleicht `Language`
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, lastLogin, xp, darkmode,
                lastname, name, plz, ort, field, aboutme, role, language);
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                ", xp=" + xp +
                ", darkmode=" + darkmode +
                ", lastname='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", plz='" + plz + '\'' +
                ", ort='" + ort + '\'' +
                ", field='" + field + '\'' +
                ", aboutme='" + aboutme + '\'' +
                ", role=" + role +
                ", language=" + language +
                '}';
    }
}