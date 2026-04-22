package dev.ohhoonim.post.activity.out;

import java.util.List;
import dev.ohhoonim.components.annotation.JpaRepository;
import dev.ohhoonim.post.infra.adapter.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, String>{
    
    List<ReplyEntity> repliesByPostId(String postId);
}
