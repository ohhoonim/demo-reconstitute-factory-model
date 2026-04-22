package dev.ohhoonim.post.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dev.ohhoonim.components.annotation.AggregateRoot;

@AggregateRoot
public class Post {
    private EntityId postId;
    private PostStatus status;
    private String title;
    private String contents;
    private Audit audit;

    private List<Reply> replies;

    public Post() { }

    public Post(EntityId postId, String operator) {
        this.postId = postId;
        this.audit = new Audit(LocalDateTime.now(), operator, null, null);
    }

    private Post(EntityId postId, String title, String contents, Audit audit, List<Reply> replies) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.audit = audit;
        this.replies = replies;
    }

    public static Post reconsitute(EntityId postId, String title, String contents, Audit audit) {
        return new Post(postId, title, contents, audit, null);
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

    public EntityId postId() {
        return this.postId;
    }

    public String title() {
        return this.title;
    }


    public String contents() {
        return this.contents;
    }

    public Audit audit() {
        return this.audit;
    }

    public PostStatus getStatus() {
        return this.status;
    }
}
