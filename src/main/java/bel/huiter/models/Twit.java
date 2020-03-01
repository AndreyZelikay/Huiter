package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "twit")
public class Twit {
    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    long id;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @JoinColumn(name = "body")
    private String body;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @JoinColumn(name = "topic")
    private String topic;

    @JsonView(bel.huiter.Json.JsonView.Twit.class)
    @OneToMany(mappedBy = "twit", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "twit_tag",
            joinColumns = @JoinColumn(name = "twit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    private Date date;

    @JsonView(bel.huiter.Json.JsonView.Twit.class)
    private int likes;

    @JsonView(bel.huiter.Json.JsonView.Twit.class)
    private int dislikes;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
