package dev.ohhoonim.post.activity.out;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import dev.ohhoonim.post.model.PostComponent;

public interface PostRepository {

    List<Map<String, Object>> findAll(List<Class<? extends PostComponent>> columnTypes);

    Optional<Map<String, Object>> findById(String value, List<Class<? extends PostComponent>> columnTypes);
    
}
