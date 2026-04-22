package dev.ohhoonim.components;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import dev.ohhoonim.components.model.payload.Response;
import dev.ohhoonim.components.model.payload.ResponseCode;

public class CommonUtil {

   public static HandlerFilterFunction<ServerResponse, ServerResponse> defaultResponse() {
        return (ServerRequest request, HandlerFunction<ServerResponse> next) -> {
            try {
                ServerResponse response = next.handle(request);

                // 1. Check if it's already a Response or a Resource
                if (response instanceof EntityResponse<?> entityResponse) {
                    Object body = entityResponse.entity();
                    if (body instanceof Response || body instanceof Resource) {
                        return response;
                    }

                    MediaType contentType = response.headers().getContentType();
                    if (contentType != null && !contentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                        return response;
                    }

                    return ServerResponse.from(response).contentType(MediaType.APPLICATION_JSON)
                            .body(new Response.Success<>(ResponseCode.SUCCESS, body));
                } else if (response.statusCode().is2xxSuccessful()) {
                    // Handler returning void (e.g., .ok().build())
                    return ServerResponse.from(response).contentType(MediaType.APPLICATION_JSON)
                            .body(new Response.Success<>(ResponseCode.SUCCESS, null));
                }

                return response;
            } catch (Exception e) {
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(new Response.Fail<>(ResponseCode.ERROR, e.getMessage(), null));
            }
        };
    }

}
