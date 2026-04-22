package dev.ohhoonim.post.model;

import java.time.LocalDateTime;

public record Reply(Long replyId, Long postId, String contents, LocalDateTime createdAt,
        String createdBy) {
}