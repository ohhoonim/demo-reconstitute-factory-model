package dev.ohhoonim.post.model;

import java.util.List;
import dev.ohhoonim.components.model.state.PostAction;
import dev.ohhoonim.components.model.state.TransitionResult;

public record PostTransitionResult(PostStatus status, List<PostAction<Post>> actions)
        implements TransitionResult<PostStatus, Post> {
}
