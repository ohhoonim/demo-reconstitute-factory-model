package dev.ohhoonim.components.model.state;

import java.util.List;

public interface TransitionResult <S, C>{
    
    S status();

    List<PostAction<C>> actions();
}
