package dev.ohhoonim.post.endpoint;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import dev.ohhoonim.post.application.PostService;

@Component
public class PostHandler {

    public final HandlerFunction<ServerResponse> posts;
    public final HandlerFunction<ServerResponse> post;
    public final HandlerFunction<ServerResponse> replies;

    public PostHandler(@Qualifier("richPostService") PostService service) {
        this.posts = request -> ServerResponse.ok().body(service.posts());
        this.post = request -> {
            var postId = requiredId(request);
            return ServerResponse.ok().body(service.post(postId));
        };
        this.replies = request -> {
            var postId = requiredId(request);
            return ServerResponse.ok().body(service.replies(postId));
        };
    }

    private String requiredId(ServerRequest request) {
        return Objects.requireNonNull(request.pathVariable("postId"), "post id는 필수입니다.");
    }
}


@Configuration
class EndpointRouter {

    @Bean
    RouterFunction<ServerResponse> postRouter(PostHandler handler) {
        return RouterFunctions.route()
                .path("/rich",
                        builder -> builder.GET("/posts", handler.posts)
                                .GET("/posts/{postId}", handler.post)
                                .GET("/posts/{postId}/replies", handler.replies))
                .build();
    }
}
