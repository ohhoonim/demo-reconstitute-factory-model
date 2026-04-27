package dev.ohhoonim.post.model;

import dev.ohhoonim.components.model.state.Status;
import dev.ohhoonim.components.model.state.TransitionResult;
import dev.ohhoonim.post.model.PostStatus.InProgress;
import dev.ohhoonim.post.model.PostStatus.None;
import dev.ohhoonim.post.model.PostStatus.Published;
import dev.ohhoonim.post.model.PostTransitionEvent.Draft;
import dev.ohhoonim.post.model.PostTransitionEvent.Save;

public sealed interface PostStatus extends Status<PostStatus, PostTransitionEvent, Post> permits Published, InProgress, None {

    public record None() implements PostStatus {
        @Override
        public TransitionResult<PostStatus, Post> trigger(PostTransitionEvent event) {
            return switch(event) {
                case Draft e -> new PostTransitionResult(new InProgress(), e.actions());
                case Save e -> new PostTransitionResult(new Published(), e.actions());
                default -> throw new RuntimeException("처리할 수 없는 상태 정보입니다.");
            };
        }

    }
    public record Published() implements PostStatus{
        @Override
        public TransitionResult<PostStatus, Post> trigger(PostTransitionEvent event) {
            return switch(event) {
                case Draft e -> new PostTransitionResult(new InProgress(), e.actions());
                default -> throw new RuntimeException("처리할 수 없는 상태 정보입니다.");
            };
        }}

    public record InProgress() implements PostStatus{
        @Override
        public TransitionResult<PostStatus, Post> trigger(PostTransitionEvent event) {
            return switch(event) {
                case Draft e -> new PostTransitionResult(new InProgress(), e.actions());
                case Save e -> new PostTransitionResult(new Published(), e.actions());
                default -> throw new RuntimeException("처리할 수 없는 상태 정보입니다.");
            };
        }
    }

    public static PostStatus valueOf(String status) {
        return switch(status) {
            case "NONE" -> new None();
            case "PUBLISHED" -> new Published();
            case "INPROGRESS" -> new InProgress();
            default -> throw new RuntimeException("처리할 수 없는 상태 정보입니다.");
        };
    }

    public default String toValue() {
        return this.getClass().getSimpleName().toUpperCase();    
    }
}
