package dev.ohhoonim.post.infra.activity;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.PostQueryActivity;
import dev.ohhoonim.post.activity.out.PostRepository;
import dev.ohhoonim.post.activity.out.ReplyRepository;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.Reply;

@Component
public class PostQueryActions implements PostQueryActivity {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    public PostQueryActions(PostRepository postRepository, ReplyRepository replyRepository) {
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public List<Post> posts() {
        return postRepository.findAll().stream().map(entity -> entity.toModel(null)).toList();
    }

    @Override
    public Post post(Post post) {
        Objects.requireNonNull(post.postId(), "Post Id 는 필수입니다.");
        var reconstitutePost = postRepository.findById(Long.valueOf(post.postId().value()))
                .map(entity -> entity.toModel(post))
                .orElseThrow(() -> new RuntimeException("Post가 존재하지 않습니다."));

        var replies = toReply(post, 2L);
        reconstitutePost.addReplies(replies);

        return reconstitutePost;
    }

    @Override
    public List<Reply> replies(Post post) {
        Objects.requireNonNull(post.postId(), "Post Id 는 필수입니다.");
        return toReply(post, 100L);
    }

    private List<Reply> toReply(Post post, Long maxLimit) {
        return replyRepository.repliesByPostId(Long.valueOf(post.postId().value())).stream()
                .map(entity -> new Reply(entity.getReplyId(), entity.getPostId(),
                        entity.getContents(), entity.getCreatedAt(), entity.getCreatedBy()))
                .limit(maxLimit).toList();
    }

}
