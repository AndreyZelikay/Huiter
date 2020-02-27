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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
