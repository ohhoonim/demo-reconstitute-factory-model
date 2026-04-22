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
import dev.ohhoonim.components.model.payload.Response;
import dev.ohhoonim.components.model.payload.Response.Success;
import dev.ohhoonim.components.model.payload.ResponseCode;
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

    private List<String> keys = List.of(
        "01KPSRPEESJXJTVWFC6VN7MSAE",
        "01KPSRPEESJXJTVWFC6VN7MSAF",
        "01KPSRPEESJXJTVWFC6VN7MSAG"
    );


    @Test
    void get_post_list() {
        var results = restTestClient.get().uri("/rich/posts").accept(MediaType.APPLICATION_JSON)
            .exchange().expectBody(new ParameterizedTypeReference<Success<List<PostDto>>>(){})
            .consumeWith(System.out::println).returnResult();

        assertThat(results.getResponseBody().code()).isEqualTo(ResponseCode.SUCCESS);
            

    }

    @Test
    void get_post_by_postid() {
        var result = restTestClient.get().uri("/rich/posts/{postId}", keys.get(1)).accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectBody(new ParameterizedTypeReference<Success<PostDto>>(){})//.consumeWith(System.out::println)
            .returnResult().getResponseBody();

        assertAll(
            () -> assertThat(result.code()).isEqualTo(ResponseCode.SUCCESS),
            () -> assertThat(result.data().title()).isEqualTo("title2"),
            () -> assertThat(result.data().replies()).hasSize(1)
        );
    }

    @Test
    void get_replies_by_post() {
        var result = restTestClient.get().uri("/rich/posts/{postId}/replies", keys.get(0)).accept(MediaType.APPLICATION_JSON)
            .exchange().expectBody(new ParameterizedTypeReference<Success<List<ReplyDto>>>() { })
            .returnResult().getResponseBody();

        assertThat(result.data()).hasSize(5);
    }

}
