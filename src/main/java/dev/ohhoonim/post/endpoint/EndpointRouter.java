package dev.ohhoonim.post.endpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import dev.ohhoonim.components.CommonUtil;

@Configuration
public class EndpointRouter {

    @Bean
    RouterFunction<ServerResponse> postRouter(PostHandler handler) {
        return RouterFunctions.route()
                .path("/rich",
                        builder -> builder.GET("/posts", handler.posts)
                                .GET("/posts/{postId}", handler.post)
                                .GET("/posts/{postId}/replies", handler.replies))
                .filter(CommonUtil.defaultResponse())
                .build();
    }
}