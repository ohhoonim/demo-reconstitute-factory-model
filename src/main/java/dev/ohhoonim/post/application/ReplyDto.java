package dev.ohhoonim.post.application;

import java.time.LocalDateTime;

public record ReplyDto(Long replyId, String contents, LocalDateTime createdAt, String createdBy) {

}
