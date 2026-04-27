package dev.ohhoonim.post.infra.adapter;

import java.lang.reflect.RecordComponent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import dev.ohhoonim.post.model.PostId;
import dev.ohhoonim.post.model.PostStatus;
import tools.jackson.databind.PropertyNamingStrategies;

@Component
public class PostFactoryAdapter implements PostArFactory {

    @Override
    public Post reconsitute(PostId id, List<Class<? extends PostComponent>> requiredVos,
            Map<String, Object> data) {

        Map<String, ? extends PostComponent> vos = requiredVos.stream().map(registry::get)
                .filter(Objects::nonNull).map(func -> func.apply(data))
                .collect(Collectors.toMap(
                    vo -> vo.getClass().getSimpleName(),
                    vo -> vo,
                    (existing, replacement) -> existing
                ));

        return Post.reconsitute(id, 
                PostStatus.valueOf(data.get("status").toString()),
                data.get("title").toString(), 
                data.get("contents").toString(),
                (PostMeta) vos.get("PostMeta"),
                Timestamp.valueOf(data.get("created_at").toString()).toInstant(),
                data.get("created_by").toString(),
                Timestamp.valueOf(data.get("modified_at").toString()).toInstant(),
                data.get("modified_by").toString());
    }

    private Map<Class<?>, Function<Map<String, Object>, PostComponent>> registry =
            Map.of(PostMeta.class, d -> new PostMeta(String.valueOf(d.get("tags")),
                    String.valueOf(d.get("permanent_link"))));

    @Override
    public List<String> resolveRequiredColumns(List<Class<? extends PostComponent>> columnTypes) {
        List<String> columns = new ArrayList<>();
        columns.addAll(List.of("post_id", "status", "title", "contents", "created_at", "created_by", "modified_at",
                "modified_by"));

        columnTypes.stream().forEach(type -> {
            var fields = Arrays.asList(type.getRecordComponents()).stream()
                    .map(RecordComponent::getName)
                    .map(s -> PropertyNamingStrategies.SNAKE_CASE.nameForField(null, null, s))
                    .toList();
            columns.addAll(fields);
        });

        return Collections.unmodifiableList(columns);
    }
}
