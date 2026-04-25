package dev.ohhoonim.post.infra.adapter;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.activity.out.PostRepository;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.PostId;

@Component
public class PostPostgresAdapter implements PostArFactory, PostRepository {


    @Override
    public Post reconsitute(PostId id, List<Class<? extends PostComponent>> requiredVos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reconsitute'");
    }

    @Override
    public List<PostEntity> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Optional<PostEntity> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    

}
