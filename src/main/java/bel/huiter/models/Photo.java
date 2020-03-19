package bel.huiter.models;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private long id;

    @JoinColumn(name = "base64_img")
    private String base64IMG;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBase64IMG() {
        return base64IMG;
    }

    public void setBase64IMG(String base64IMG) {
        this.base64IMG = base64IMG;
    }
}
