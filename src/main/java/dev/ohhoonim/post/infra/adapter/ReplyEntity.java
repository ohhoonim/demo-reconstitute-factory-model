package dev.ohhoonim.post.infra.adapter;

import java.time.LocalDateTime;
import dev.ohhoonim.components.annotation.Entity;

@Entity
public class ReplyEntity  {
    private Long replyId; 
    private Long postId; 
    private String contents; 
    private LocalDateTime createdAt;
    private String createdBy;

    public ReplyEntity() {}

    public ReplyEntity(Long replyId, Long postId, String contents, LocalDateTime createdAt,
            String createdBy) {
        this.replyId = replyId;
        this.postId = postId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    
}
