package dev.ohhoonim.post.model;

public class PostException extends RuntimeException {
   
    public PostException(String message) {
        super(message);
    }

    public PostException(String message, Throwable e) {
        super(message, e);
    }
}
