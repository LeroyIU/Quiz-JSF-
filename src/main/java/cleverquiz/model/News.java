package cleverquiz.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a post.
 */
@Entity
@Table(name = "Post")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String title;
    private String text;

    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public News() {
    }

    public News(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.date = LocalDateTime.now();
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "News{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", author=" + author +
                '}';
    }
}
