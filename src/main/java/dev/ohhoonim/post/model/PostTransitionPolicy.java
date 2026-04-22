package dev.ohhoonim.post.model;

import org.springframework.stereotype.Component;
import dev.ohhoonim.components.model.state.StateTransitionPolicy;

@Component
public interface PostTransitionPolicy extends StateTransitionPolicy<PostStatus, PostTransitionEvent, Post>{
    
}
