package cleverquiz.model;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Entity class representing a post.
 */
@Entity
@Table(name = "Post")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private String text;
    private Date date;

    public Integer getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
