package dev.ohhoonim.post;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.activity.out.PostRepository;
import dev.ohhoonim.post.activity.out.ReplyRepository;
import dev.ohhoonim.post.infra.activity.PostArMapper;
import dev.ohhoonim.post.infra.adapter.PostFactoryAdapter;
import dev.ohhoonim.post.infra.adapter.PostPostgresAdapter;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import dev.ohhoonim.post.model.PostId;
import dev.ohhoonim.post.model.PostStatus;
import dev.ohhoonim.post.model.PostStatus.None;
import dev.ohhoonim.post.model.Reply;

@Testcontainers
@JdbcTest
@Import({PostPostgresAdapter.class, PostFactoryAdapter.class})
public class PostRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer(DockerImageName.parse("postgres:18.1-alpine"));

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    PostArFactory postArFactory;

    @Autowired
    JdbcClient jdbcClient;

    Long replyId;
    PostId postId;

    @BeforeEach
    void setUp() {
        this.postId = PostId.Creator.generate();

        jdbcClient.sql("""
                       insert into tb_post ( post_id, status, title, contents,
                       tags, permanent_link, created_At, created_by, modified_at, modified_by  )
                       values (:post_id, :status, :title, :contents,
                       '','http://localhost/abc', now(), :created_by, now(), :modified_by)
                """).param("post_id", this.postId.toValue()).param("status", new None().toValue())
                .param("title", "").param("contents", "").param("created_by", "system")
                .param("modified_by", "system").update();
    }

    @Test
    @DisplayName("컬럼을 지정하여 Post 데이터를 조회할 수 있다. ")
    void post() {
        // List<Class<? extends PostComponent>> columns = List.of();
        // List<Class<? extends PostComponent>> columns = postArFactory.forDefault();
        List<Class<? extends PostComponent>> columns = List.of(PostMeta.class);
        // List<Class<? extends PostComponent>> columns = postArFactory.forOwner();
        var result = postRepository.findById(this.postId.toValue(), columns)
                .map(new PostArMapper().apply(postArFactory, columns))
                .orElseThrow(() -> new RuntimeException("post가 존재하지 않습니다."));

        assertThat(result.getId().toValue()).isEqualTo(this.postId.toValue());
        // assertThat(result.meta()).isNull();
        assertThat(result.meta().permanentLink()).isEqualTo("http://localhost/abc");
    }

    @Test
    void status() {
        PostStatus status = new None();
        assertThat(status.toValue()).isEqualTo("NONE");
    }

    @Test
    void replies() {
        var reply = new Reply(null, postId, "댓글입니다.", Instant.now(), "system");
        Long replyId = replyRepository.addReply(reply);
        assertThat(replyId).isEqualTo(1L);

        replyRepository.addReply(new Reply(null, postId, "두번째 댓글입니다.", Instant.now(), "system"));

        var replies = replyRepository.repliesByPostId(this.postId.toValue());

        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).postId()).isEqualTo(postId);
        assertThat(replies.get(0).replyId()).isEqualTo(replyId);
    }



}
