package bel.huiter.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Twit> twits;

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Twit> getTwits() {
        return twits;
    }

    public void setTwits(List<Twit> twits) {
        this.twits = twits;
    }
}
