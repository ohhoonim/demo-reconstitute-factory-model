package dev.ohhoonim.post.activity.out;

import java.util.List;
import dev.ohhoonim.components.model.factory.ArFactory;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostId;
import dev.ohhoonim.post.model.PostComponent.PostMeta;

public interface PostArFactory extends ArFactory<Post, PostId, PostComponent>{
    
    default Post forOwner(PostId id) {
        return reconsitute(id, List.of(PostMeta.class));
    }
}
