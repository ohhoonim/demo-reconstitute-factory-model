package dev.ohhoonim.post.activity;

import java.util.List;
import dev.ohhoonim.post.model.Post;

/*
@startuml

start
:Post 신규 등록;
end

start 
:Post 상세조회;

split
:Post 수정;
split again
:Post 삭제;

split again

    split
    :댓글 작성;
    split again
    :댓글 삭제;
    end split
    :Post 상세조회;
    detach

end split

:Post 목록;

end
@enduml

*/
public interface PostCommandActivity {
    
    Post registNewPost(Post post) ;

    Post modifyPost(Post post);

    void removePost(Post post);

    void registNewReply(Post post);

    void removeReply(Post post, List<String> replyId); 
}
