package dev.ohhoonim.post;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.github.f4b6a3.ulid.UlidCreator;
import dev.ohhoonim.components.model.state.PostAction;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostId;
import dev.ohhoonim.post.model.PostStatus;
import dev.ohhoonim.post.model.PostTransitionEvent;
import dev.ohhoonim.post.model.PostTransitionPolicy;

@ExtendWith(MockitoExtension.class)
public class PostEventTest {

    PostTransitionPolicy statePolicy;

    @BeforeEach
    void setup() {
        this.statePolicy= new PostTransitionPolicy() {};
    }

    @Test
    @DisplayName("임시저장시 원본의 저장상태는 InProgress이어야 한다.")
    void post_Draft_event() {
        var post = new Post(PostId.Creator.generate(), "system");

        var beforePosted = Post.reconsitute(post.getId(), "title1", "contents 1", null, Instant.now(), "matthew", Instant.now(), "matthew");
        beforePosted.setStatus(new PostStatus.None());

        // 임시 저장 데이터를 별도 버전관리 테이블에 저장할 수 도 있다.
        List<PostAction<Post>> actions = List.of(
            p -> System.out.println("입시저장 로직을 외부에서 주입. activity.versionSave"));

        beforePosted.transition(new PostTransitionEvent.Draft(actions), statePolicy);

        // ## 비즈니스 관점에서 예상되는 처리 로직
        // - infra 저장로직 
        // - 이력 관리 모듈과 연동하여 변경이력 저장

        assertThat(beforePosted.getStatus()).isInstanceOf(PostStatus.InProgress.class);

    }
}
