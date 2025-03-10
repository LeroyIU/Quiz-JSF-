package cleverquiz.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String name;

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }
}
