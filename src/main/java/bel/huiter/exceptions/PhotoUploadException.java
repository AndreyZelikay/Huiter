package bel.huiter.exceptions;

import java.io.IOException;

public class PhotoUploadException extends IOException {
    public PhotoUploadException(String message) {
        super(message);
    }

    public PhotoUploadException() {
        super();
    }
}
