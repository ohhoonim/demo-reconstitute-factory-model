package dev.ohhoonim.post.infra.activity;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.PostQueryActivity;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.activity.out.PostRepository;
import dev.ohhoonim.post.activity.out.ReplyRepository;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostComponent.PostMeta;
import dev.ohhoonim.post.model.Reply;

@Component
public class PostQueryActions implements PostQueryActivity {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PostArFactory postArFactory;

    public PostQueryActions(PostRepository postRepository, ReplyRepository replyRepository,
            PostArFactory postArFactory) {
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
        this.postArFactory = postArFactory;
    }

    @Override
    public List<Post> posts() {
        List<Class<? extends PostComponent>> columns = List.of(PostMeta.class);
        return postRepository.findAll(columns).stream().map(new PostArMapper().apply(postArFactory, columns))
                .toList();
    }

    @Override
    public Post post(Post post) {
        Objects.requireNonNull(post.getId(), "Post Id 는 필수입니다.");
        List<Class<? extends PostComponent>> columns = List.of(PostMeta.class);
        Post reconstitutePost = postRepository.findById(post.getId().toValue(), columns)
                .map(new PostArMapper().apply(postArFactory, columns))
                .orElseThrow(() -> new RuntimeException("Post가 존재하지 않습니다."));

        var replies = toReply(post, 2L);
        reconstitutePost.addReplies(replies);

        return reconstitutePost;
    }

    @Override
    public List<Reply> replies(Post post) {
        return toReply(post, 100L);
    }

    private List<Reply> toReply(Post post, Long maxLimit) {
        return replyRepository.repliesByPostId(post.getId().getRawValue()).stream().limit(maxLimit)
                .toList();
    }

}
