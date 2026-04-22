package dev.ohhoonim.post.application;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import dev.ohhoonim.post.model.PostId;
import dev.ohhoonim.post.model.Reply;

public record PostDto (
    PostId postId,
    String title,
    String contents,
    Instant createdAt,
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
    
    public PostDto (PostId postId,
    String title,
    String contents,
    Instant createdAt,
    String createdBy) {
        this(postId, title, contents, createdAt, createdBy, null);
    }
}
