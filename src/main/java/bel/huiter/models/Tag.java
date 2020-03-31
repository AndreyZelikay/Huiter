package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "body")
    private String body;

    @JsonBackReference
    @ManyToMany(mappedBy = "tags")
    private List<Twit> twits;

    @Column(name = "searchCounter")
    private long searchCounter;

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

    public List<Twit> getTwits() {
        return twits;
    }

    public void setTwits(List<Twit> twits) {
        this.twits = twits;
    }

    public long getSearchCounter() {
        return searchCounter;
    }

    public void setSearchCounter(long searchCounter) {
        this.searchCounter = searchCounter;
    }
}
