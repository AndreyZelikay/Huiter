package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @JsonView({bel.huiter.Json.JsonView.User.class,
            bel.huiter.Json.JsonView.JWT.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @JsonView({bel.huiter.Json.JsonView.Twit.class,
            bel.huiter.Json.JsonView.User.class,
            bel.huiter.Json.JsonView.JWT.class,
            bel.huiter.Json.JsonView.Comment.class})
    @Column(name = "name")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @JsonView(bel.huiter.Json.JsonView.User.class)
    @Column(name = "status")
    private String status;

    @JsonView(bel.huiter.Json.JsonView.User.class)
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Twit> twits;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo userPhoto;

    public User() {
        twits = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Twit> getTwits() {
        return twits;
    }

    public void setTwits(Set<Twit> twits) {
        this.twits = twits;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Photo getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Photo userPhoto) {
        this.userPhoto = userPhoto;
    }
}
