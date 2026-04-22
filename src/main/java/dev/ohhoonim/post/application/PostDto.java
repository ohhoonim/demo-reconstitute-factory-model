package dev.ohhoonim.post.application;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import dev.ohhoonim.post.model.Reply;

public record PostDto (
    String postId,
    String title,
    String contents,
    LocalDateTime createdAt,
    String createdBy ,
    List<Reply> replies
){
    public PostDto {
        if (replies != null) {
            replies = Collections.unmodifiableList(replies);
        } else {
            replies = Collections.emptyList();
        }
    }
    
    public PostDto (String postId,
    String title,
    String contents,
    LocalDateTime createdAt,
    String createdBy) {
        this(postId, title, contents, createdAt, createdBy, null);
    }
}
