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
    @Column(name = "id")
    long id;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @Column(name = "body", columnDefinition = "text")
    private String body;

    @JsonView({bel.huiter.Json.JsonView.Twit.class})
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @Column(name = "topic")
    private String topic;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    @JsonView(bel.huiter.Json.JsonView.Twit.class)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "twit_photo",
            joinColumns = @JoinColumn(name = "twit_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id"))
    private Set<Photo> photos;

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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public void addLIke() {
        likes++;
    }

    public void addDislike() {
        dislikes++;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
