package cleverquiz.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity class representing a friendship relationship between users.
 */
@Entity
@Table(name = "Friends")
public class Friends {
    @EmbeddedId
    private FriendsId id;
}