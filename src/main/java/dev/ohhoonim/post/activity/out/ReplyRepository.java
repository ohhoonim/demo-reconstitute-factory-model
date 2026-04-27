package dev.ohhoonim.post.activity.out;

import java.util.List;
import dev.ohhoonim.post.model.Reply;

public interface ReplyRepository {
    
    List<Reply> repliesByPostId(String postId);

    void saveReply(Reply reply);

    Long addReply(Reply reply);
}
