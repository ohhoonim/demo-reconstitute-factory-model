package dev.ohhoonim.post.application;

import java.time.Instant;

public record ReplyDto(Long replyId, String contents, Instant createdAt, String createdBy) {

}
