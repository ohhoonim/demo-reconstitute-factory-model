package dev.ohhoonim.post.infra.activity;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostId;

public class PostArMapper implements
        BiFunction<PostArFactory, List<Class<? extends PostComponent>>, Function<Map<String, Object>, Post>> {

    @Override
    public Function<Map<String, Object>, Post> apply(PostArFactory factory,
            List<Class<? extends PostComponent>> voList) {
        return data -> factory.reconsitute(PostId.Creator.from(data.get("post_id").toString()),
                voList, data);
    }
}
