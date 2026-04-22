package dev.ohhoonim.components.model.state;

import java.util.List;

public interface TransitionEvent<S, C> {
    List<PostAction<C>> actions();
}
