package dev.ohhoonim.post.model;

import dev.ohhoonim.post.model.PostComponent.PostMeta;

public sealed interface PostComponent permits PostMeta {
    public record PostMeta(String tags, String permanentLink) implements PostComponent {
    }
}
