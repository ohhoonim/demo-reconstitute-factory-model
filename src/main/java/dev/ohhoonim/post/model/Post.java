package dev.ohhoonim.post.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dev.ohhoonim.components.annotation.AggregateRoot;
import dev.ohhoonim.components.model.unit.BaseEntity;
import dev.ohhoonim.post.model.PostComponent.PostMeta;

@AggregateRoot
public class Post extends BaseEntity<PostId> {
    private PostStatus status;
    private String title;
    private String contents;
    private PostMeta meta;

    private List<Reply> replies;

    public Post(PostId postId, String operator) {
        super(postId, operator);
    }

    private Post(PostId postId, PostStatus status, String title, String contents, PostMeta meta, Instant createdAt, String createdBy,
            Instant modifiedAt, String modifiedBy, List<Reply> replies) {
        super(postId, createdAt, createdBy, modifiedAt, modifiedBy);
        this.status = status;
        this.title = title;
        this.contents = contents;
        this.meta = meta;
        this.replies = replies;
    }

    public static Post reconsitute(PostId postId, PostStatus status, String title, String contents, PostMeta meta, Instant createdAt,
            String createdBy, Instant modifiedAt, String modifiedBy) {
        return new Post(postId, status, title, contents, meta, createdAt, createdBy, modifiedAt, modifiedBy,
                null);
    }

    public void transition(PostTransitionEvent event, PostTransitionPolicy policy) {
        var transitionResult = policy.transition(this.status, event);
        this.setStatus(transitionResult.status());
        transitionResult.actions().forEach(action -> action.followup(this));
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public void addReplies(List<Reply> replies) {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
        this.replies.addAll(replies);
    }

    public List<Reply> getReplies() {
        return Collections.unmodifiableList(this.replies);
    }

    public String title() {
        return this.title;
    }


    public String contents() {
        return this.contents;
    }

    public PostStatus getStatus() {
        return this.status;
    }

    public PostMeta meta() {
        return this.meta;
    }
}
