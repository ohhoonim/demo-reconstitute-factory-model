package dev.ohhoonim.post.infra.activity;

import java.util.List;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.PostCommandActivity;
import dev.ohhoonim.post.model.Post;

@Component
public class PostCommandActions implements PostCommandActivity {

    @Override
    public Post registNewPost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registNewPost'");
    }

    @Override
    public Post modifyPost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyPost'");
    }

    @Override
    public void removePost(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removePost'");
    }

    @Override
    public void registNewReply(Post post) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registNewReply'");
    }

    @Override
    public void removeReply(Post post, List<String> replyId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeReply'");
    }
    
}
