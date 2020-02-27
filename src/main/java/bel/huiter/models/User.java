package bel.huiter.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private long id;

    @JoinColumn(name = "name")
    private String name;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "status")
    private String status;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Twit> twits;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public User() {
        twits = new ArrayList<>();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
}
