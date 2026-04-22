package dev.ohhoonim.post.model;

import java.time.Instant;

public record Reply(Long replyId, PostId postId, String contents, Instant createdAt,
        String createdBy) {
}