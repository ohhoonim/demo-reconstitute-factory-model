package dev.ohhoonim.post.application;

import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import dev.ohhoonim.components.annotation.Transactional;
import dev.ohhoonim.post.activity.PostQueryActivity;
import dev.ohhoonim.post.model.EntityId;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.Reply;

@Service("richPostService")
public class PostService {

    private final PostQueryActivity postQueryActivity;

    public PostService(PostQueryActivity postQueryActivity) {
        this.postQueryActivity = postQueryActivity;
    }

    @Transactional(readOnly = true)
    public List<PostDto> posts() {
        return postQueryActivity.posts().stream().map(this.postToDto).toList();
    }

    @Transactional(readOnly = true)
    public PostDto post(String postId) {
        Post reqPost = new Post(new EntityId(postId), "system"); // 여기서는 operator를 임의로 지정
        Post post = postQueryActivity.post(reqPost);

        return new PostDto(postId, post.title(), post.contents(), 
                post.audit().createdAt(), post.audit().createdBy(), post.getReplies());
    }

    @Transactional(readOnly = true)
    public List<ReplyDto> replies(String postId) {
        Post reqPost = new Post(new EntityId(postId), "system");
        return postQueryActivity.replies(reqPost).stream().map(replyToDto).toList();
    }

    private final Function<Post, PostDto> postToDto =
            post -> new PostDto(String.valueOf(post.postId()), post.title(), post.contents(),
                    post.audit().createdAt(), post.audit().createdBy());

    private final Function<Reply, ReplyDto> replyToDto =
            repl -> new ReplyDto(repl.postId(), repl.contents(), repl.createdAt(), repl.createdBy());
}
