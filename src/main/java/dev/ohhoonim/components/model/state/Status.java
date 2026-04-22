package dev.ohhoonim.components.model.state;

public interface Status <S, T, C> {
    TransitionResult<S, C> trigger(T event);

    default String toValue() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
