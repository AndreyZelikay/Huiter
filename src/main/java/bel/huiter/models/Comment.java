package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class Comment {

    @JsonView(bel.huiter.json.JsonView.Comment.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @JsonView(bel.huiter.json.JsonView.Comment.class)
    @Column(name = "body", columnDefinition = "text")
    private String body;

    @JsonView(bel.huiter.json.JsonView.Comment.class)
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "twit_id")
    private Twit twit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Twit getTwit() {
        return twit;
    }

    public void setTwit(Twit twit) {
        this.twit = twit;
    }
}
