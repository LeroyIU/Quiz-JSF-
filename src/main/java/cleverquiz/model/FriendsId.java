package cleverquiz.model;

import jakarta.persistence.Embeddable;

/**
 * Embeddable class representing the composite key for Friends.
 */
@Embeddable
public class FriendsId implements java.io.Serializable {
    private Integer userId1;
    private Integer userId2;
}