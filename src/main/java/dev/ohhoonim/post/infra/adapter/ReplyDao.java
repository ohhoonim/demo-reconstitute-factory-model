package dev.ohhoonim.post.infra.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import dev.ohhoonim.post.activity.out.ReplyRepository;

@Repository("richReplyDao")
public class ReplyDao implements ReplyRepository {


    private final static List<ReplyEntity> replies = List.of(
        new ReplyEntity(1l, 1L, "이게뭐야 1", LocalDateTime.now(), "matthew 1"),
        new ReplyEntity(2l, 2L, "이게뭐야 2", LocalDateTime.now(), "matthew 2"),
        new ReplyEntity(2l, 1L, "이게뭐야 2", LocalDateTime.now(), "matthew 2"),
        new ReplyEntity(3l, 2L, "이게뭐야 3", LocalDateTime.now(), "matthew 3"),
        new ReplyEntity(4l, 3L, "이게뭐야 4", LocalDateTime.now(), "matthew 4"),
        new ReplyEntity(5l, 1L, "이게뭐야 5", LocalDateTime.now(), "matthew 5"),
        new ReplyEntity(6l, 1L, "이게뭐야 6", LocalDateTime.now(), "matthew 6")
    );

    @Override
    public List<ReplyEntity> repliesByPostId(Long postId) {
        return replies.stream().filter(r -> r.getPostId().equals(postId)).toList();
    }


    @Override
    public List<ReplyEntity> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<ReplyEntity> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    
}
