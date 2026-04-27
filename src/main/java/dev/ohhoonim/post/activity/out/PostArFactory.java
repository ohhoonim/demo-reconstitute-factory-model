package dev.ohhoonim.post.activity.out;

import java.util.List;
import dev.ohhoonim.components.model.factory.ArFactory;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import dev.ohhoonim.post.model.PostId;

public interface PostArFactory extends ArFactory<Post, PostId, PostComponent> {
    
    public default List<Class<? extends PostComponent>> forOwner() {
        return List.of(PostMeta.class);
    }

    public default List<Class<? extends PostComponent>> forDefault() {
        return List.of();
    }

}
