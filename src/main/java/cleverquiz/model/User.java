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
    private boolean admin;
    private Date lastLogin;
    private Integer xp;
    private int gameCount;
    private String lastname;
    private String favColour;
    private String favCategory;
    private String favMusic;
    private String favFood;
    private String name;
    private String aboutme;

    public static User create(String name, String email, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFavColour() {
        return favColour;
    }

    public void setFavColour(String favColour) {
        this.favColour = favColour;
    }

    public String getFavCategory() {
        return favCategory;
    }

    public void setFavCategory(String favCategory) {
        this.favCategory = favCategory;
    }

    public String getFavMusic() {
        return favMusic;
    }

    public void setFavMusic(String favMusic) {
        this.favMusic = favMusic;
    }

    public String getFavFood() {
        return favFood;
    }

    public void setFavFood(String favFood) {
        this.favFood = favFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return admin == user.admin && gameCount == user.gameCount && Objects.equals(userId, user.userId) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(lastLogin, user.lastLogin) && Objects.equals(xp, user.xp) && Objects.equals(lastname, user.lastname) && Objects.equals(favColour, user.favColour) && Objects.equals(favCategory, user.favCategory) && Objects.equals(favMusic, user.favMusic) && Objects.equals(favFood, user.favFood) && Objects.equals(name, user.name) && Objects.equals(aboutme, user.aboutme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, admin, lastLogin, xp, gameCount, lastname, favColour, favCategory, favMusic, favFood, name, aboutme);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", lastLogin=" + lastLogin +
                ", xp=" + xp +
                ", gameCount=" + gameCount +
                ", lastname='" + lastname + '\'' +
                ", favColour='" + favColour + '\'' +
                ", favCategory='" + favCategory + '\'' +
                ", favMusic='" + favMusic + '\'' +
                ", favFood='" + favFood + '\'' +
                ", name='" + name + '\'' +
                ", aboutme='" + aboutme + '\'' +
                '}';
    }
}