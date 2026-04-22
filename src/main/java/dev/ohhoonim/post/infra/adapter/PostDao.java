package dev.ohhoonim.post.infra.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import dev.ohhoonim.post.activity.out.PostRepository;

@Repository("richPostDao")
public class PostDao implements PostRepository {

    private final static List<PostEntity> posts = List.of(
        new PostEntity(1l, "title1", "contents 1", LocalDateTime.now(), "matthew", null, null),
        new PostEntity(2l, "title2", "contents 2", LocalDateTime.now(), "alison", null, null),
        new PostEntity(3l, "title3", "contents 3", LocalDateTime.now(), "ohhoonim", null, null)
    );

    @Override
    public List<PostEntity> findAll() {
        return posts;
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return posts.stream().filter(p -> p.getPostId().equals(id)).findFirst();
    }
    
}
