package dev.ohhoonim.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import dev.ohhoonim.post.application.PostDto;
import dev.ohhoonim.post.application.ReplyDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RichTest {
    @LocalServerPort
    private int serverPort;

    private RestTestClient restTestClient;

    @BeforeEach
    void setup() {
        this.restTestClient = RestTestClient.bindToServer().baseUrl("http://localhost:" + this.serverPort).build();
    }

    @Test
    void get_post_list() {
        var results = restTestClient.get().uri("/rich/posts").accept(MediaType.APPLICATION_JSON)
            .exchange().expectBody(new ParameterizedTypeReference<List<PostDto>>(){})
            .returnResult().getResponseBody() ;

        assertThat(results).hasSize(3);
    }

    @Test
    void get_post_by_postid() {
        var result = restTestClient.get().uri("/rich/posts/{postId}", 1L).accept(MediaType.APPLICATION_JSON)
            .exchange().expectBody(new ParameterizedTypeReference<PostDto>(){})
            .returnResult().getResponseBody();

        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result.title()).isEqualTo("title1"),
            () -> assertThat(result.replies()).hasSize(2)
        );
    }

    @Test
    void get_replies_by_post() {
        var result = restTestClient.get().uri("/rich/posts/{postId}/replies", 1L).accept(MediaType.APPLICATION_JSON)
            .exchange().expectBody(new ParameterizedTypeReference<List<ReplyDto>>() { })
            .returnResult().getResponseBody();

        assertThat(result).hasSize(4);
    }

}
