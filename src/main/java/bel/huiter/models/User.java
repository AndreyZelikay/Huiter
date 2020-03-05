package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @JsonView({bel.huiter.Json.JsonView.User.class, bel.huiter.Json.JsonView.JWT.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private long id;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class, bel.huiter.Json.JsonView.JWT.class})
    @JoinColumn(name = "name")
    private String name;

    @JsonView(bel.huiter.Json.JsonView.User.class)
    @JoinColumn(name = "password")
    private String password;

    @JsonView(bel.huiter.Json.JsonView.User.class)
    @JoinColumn(name = "status")
    private String status;

    @JsonView(bel.huiter.Json.JsonView.User.class)
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Twit> twits;

    @JsonView({bel.huiter.Json.JsonView.Twit.class, bel.huiter.Json.JsonView.User.class})
    private String base64Img;

    public User() {
        twits = new ArrayList<>();
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

    public List<Twit> getTwits() {
        return twits;
    }

    public void setTwits(List<Twit> twits) {
        this.twits = twits;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }
}
