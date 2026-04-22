package dev.ohhoonim.post.activity;

import java.util.List;
import dev.ohhoonim.post.model.Post;
import dev.ohhoonim.post.model.Reply;

/*
@startuml
start
:post 목록 조회;
floating note left: 목록은 10개씩

:post 상세 조회;
    note right
    댓글은 최초 1개만 불러오고 
    `더보기` 버튼을 이용하여 
    댓글을 추가로 불러온다
    end note

end
@enduml
*/
public interface PostQueryActivity {
    
    List<Post> posts(); 

    Post post(Post post);

    List<Reply> replies(Post post);
}
