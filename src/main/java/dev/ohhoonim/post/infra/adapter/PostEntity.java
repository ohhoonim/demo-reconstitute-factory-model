package dev.ohhoonim.post.infra.adapter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import dev.ohhoonim.components.ToModel;
import dev.ohhoonim.components.annotation.Entity;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import dev.ohhoonim.post.model.PostId;

@Entity
public class PostEntity implements ToModel<Post> {

    private String postId;
    private String title;
    private String contents;
    private String tags;
    private String permanentLink;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifedBy;

    public PostEntity() {}

    public PostEntity(String postId, String title, String contents, String tags,
            String permanentLink, LocalDateTime createdAt, String createdBy,
            LocalDateTime modifiedAt, String modifedBy) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.tags = tags;
        this.permanentLink = permanentLink;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = modifiedAt;
        this.modifedBy = modifedBy;
    }



    @Override
    public Post toModel(Post post) {
        if (post == null) {
            post = new Post(PostId.Creator.from(this.postId), this.createdBy);
        }
        var meta = new PostMeta(tags, permanentLink);
        return Post.reconsitute(post.getId(), title, contents, meta,
                createdAt.toInstant(ZoneOffset.UTC), createdBy,
                this.getModifiedAt().toInstant(ZoneOffset.UTC), modifedBy);
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDateTime getModifiedAt() {
        return this.modifiedAt == null ? getCreatedAt() : this.modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getModifedBy() {
        return this.modifedBy;
    }

    public void setModifedBy(String modifedBy) {
        this.modifedBy = modifedBy;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPermanentLink() {
        return permanentLink;
    }

    public void setPermanentLink(String permanentLink) {
        this.permanentLink = permanentLink;
    }

}
