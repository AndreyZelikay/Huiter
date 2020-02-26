package bel.huiter.models;

import javax.persistence.*;

@Entity
public class Twit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String text;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User owner;

    private String topic;

}
