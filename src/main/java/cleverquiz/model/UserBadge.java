package cleverquiz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "User_Badge")
@IdClass(UserBadgeId.class)
public class UserBadge {
    @Id
    private Integer userId;
    @Id
    private Integer badgeId;
}
