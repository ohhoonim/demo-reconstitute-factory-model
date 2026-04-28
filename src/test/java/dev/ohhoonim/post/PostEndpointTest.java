package dev.ohhoonim.post;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import dev.ohhoonim.post.application.PostDto;
import dev.ohhoonim.post.application.PostService;
import dev.ohhoonim.post.application.ReplyDto;
import dev.ohhoonim.post.endpoint.EndpointRouter;
import dev.ohhoonim.post.endpoint.PostHandler;
import dev.ohhoonim.post.model.PostId;

@WebMvcTest({ PostHandler.class, EndpointRouter.class })
@ExtendWith(RestDocumentationExtension.class)
@DisplayName("Post API 엔드포인트 테스트")
class PostEndpointTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean(name = "richPostService")
    private PostService postService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocs, WebApplicationContext context) {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocs).operationPreprocessors()
                        .withRequestDefaults(prettyPrint()).withResponseDefaults(prettyPrint()))
                .build();
        this.mvc = MockMvcTester.create(mockMvc);
    }

    @Test
    @DisplayName("전체 게시글 목록을 조회한다")
    void posts_test() {
        // Given
        given(postService.posts()).willReturn(List.of(
                new PostDto(PostId.Creator.generate(), "Title 1", "Contents 1", Instant.now(), "user1")));

        // When
        var result = mvc.get().uri("/rich/posts").exchange();

        // Then & Document
        result.assertThat().apply(document("posts-list",
                responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("data").description("게시글 목록"),
                        fieldWithPath("data[].postId").description("게시글 ID"),
                        fieldWithPath("data[].title").description("제목"),
                        fieldWithPath("data[].contents").description("내용"),
                        fieldWithPath("data[].createdAt").description("작성일시"),
                        fieldWithPath("data[].createdBy").description("작성자"),
                        fieldWithPath("data[].replies").description("댓글 목록"))));

        result.assertThat().hasStatusOk();
    }

    @Test
    @DisplayName("단일 게시글을 상세 조회한다")
    void post_detail_test() {
        // Given
        String postId = "post-1";
        given(postService.post(anyString())).willReturn(
                new PostDto(PostId.Creator.generate(), "Title 1", "Contents 1", Instant.now(), "user1"));

        // When
        var result = mvc.get().uri("/rich/posts/{postId}", postId).exchange();

        // Then & Document
        result.assertThat().apply(document("post-detail",
                pathParameters(
                        parameterWithName("postId").description("게시글 ID")),
                responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("data").description("게시글 상세 정보"),
                        fieldWithPath("data.postId").description("게시글 ID"),
                        fieldWithPath("data.title").description("제목"),
                        fieldWithPath("data.contents").description("내용"),
                        fieldWithPath("data.createdAt").description("작성일시"),
                        fieldWithPath("data.createdBy").description("작성자"),
                        fieldWithPath("data.replies").description("댓글 목록"))));

        result.assertThat().hasStatusOk();
    }

    @Test
    @DisplayName("게시글의 댓글 목록을 조회한다")
    void replies_test() {
        // Given
        String postId = PostId.Creator.generate().toValue();
        given(postService.replies(anyString())).willReturn(List.of(
                new ReplyDto(1L, "Reply contents", Instant.now(), "replier1")));

        // When
        var result = mvc.get().uri("/rich/posts/{postId}/replies", postId).exchange();

        // Then & Document
        result.assertThat().apply(document("replies-list",
                pathParameters(
                        parameterWithName("postId").description("게시글 ID")),
                responseFields(
                        fieldWithPath("code").description("응답 코드"),
                        fieldWithPath("data").description("댓글 목록"),
                        fieldWithPath("data[].replyId").description("댓글 ID"),
                        fieldWithPath("data[].contents").description("내용"),
                        fieldWithPath("data[].createdAt").description("작성일시"),
                        fieldWithPath("data[].createdBy").description("작성자"))));

        result.assertThat().hasStatusOk();
    }
}
