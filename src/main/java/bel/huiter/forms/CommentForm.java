package bel.huiter.forms;

public class CommentForm {

    private Long commentId;
    private Long twitId;
    private String currentUserJWT;
    private String body;

    public String getCurrentUserJWT() {
        return currentUserJWT;
    }

    public void setCurrentUserJWT(String currentUserJWT) {
        this.currentUserJWT = currentUserJWT;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getTwitId() {
        return twitId;
    }

    public void setTwitId(Long twitId) {
        this.twitId = twitId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
