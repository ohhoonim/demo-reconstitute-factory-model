package dev.ohhoonim.post.activity.out;

import dev.ohhoonim.components.annotation.JpaRepository;
import dev.ohhoonim.post.infra.adapter.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    
}
