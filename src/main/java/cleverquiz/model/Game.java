package cleverquiz.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    private Integer duration;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Event_ID")
    private Event event;
}
