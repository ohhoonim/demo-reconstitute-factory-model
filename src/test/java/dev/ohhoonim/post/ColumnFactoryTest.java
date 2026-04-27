package dev.ohhoonim.post;

import static org.assertj.core.api.Assertions.assertThat;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import tools.jackson.databind.PropertyNamingStrategies;

public class ColumnFactoryTest {

    @Test
    void resolveRequiredColumns() {

        List<Class<? extends PostComponent>> columnTypes = List.of(PostMeta.class);

        List<String> columns = new ArrayList<>();
        columns.addAll(List.of("post_id", "status", "created_at", "created_by", "modified_at",
                "modified_by"));

        columnTypes.stream().forEach(type -> {
            var fields = Arrays.asList(type.getRecordComponents()).stream()
                .map(RecordComponent::getName)
                .map(s -> PropertyNamingStrategies.SNAKE_CASE.nameForField(null, null, s)) 
                .toList();
            columns.addAll(fields);


        });

        assertThat(columns).hasSize(8);
        assertThat(columns.contains("tags")).isTrue();
    }
}
