package dev.ohhoonim.post.model;

import java.util.Collections;
import java.util.List;
import dev.ohhoonim.components.model.state.PostAction;
import dev.ohhoonim.components.model.state.TransitionEvent;
import dev.ohhoonim.post.model.PostTransitionEvent.Draft;
import dev.ohhoonim.post.model.PostTransitionEvent.Save;

public sealed interface PostTransitionEvent extends TransitionEvent<PostStatus, Post>
        permits Save, Draft {

    public record Save() implements PostTransitionEvent {

        @Override
        public List<PostAction<Post>> actions() {
            return Collections.emptyList();
        }
    }

    public record Draft(List<PostAction<Post>> actions) implements PostTransitionEvent {
    }



}
