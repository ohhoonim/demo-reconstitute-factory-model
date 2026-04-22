package dev.ohhoonim.post;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import dev.ohhoonim.post.model.PostStatus;

public class GrammarTest {
    @Test
    void record_to_string() {
        var draft = new PostStatus.Published();
        assertThat(draft.toValue()).isEqualTo("PUBLISHED");
    }
}
