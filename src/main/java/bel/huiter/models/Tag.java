package bel.huiter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return body.equals(tag.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

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
