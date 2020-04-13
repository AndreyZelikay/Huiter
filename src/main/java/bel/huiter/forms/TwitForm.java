package bel.huiter.forms;

import bel.huiter.models.Tag;

import java.util.List;

public class TwitForm {

    private Long id;
    private String currentUserJWT;
    private String body;
    private List<Tag> tags;
    private List<String> base64Photos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCurrentUserJWT() {
        return currentUserJWT;
    }

    public void setCurrentUserJWT(String currentUserJWT) {
        this.currentUserJWT = currentUserJWT;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<String> getBase64Photos() {
        return base64Photos;
    }

    public void setBase64Photos(List<String> base64Photos) {
        this.base64Photos = base64Photos;
    }
}
