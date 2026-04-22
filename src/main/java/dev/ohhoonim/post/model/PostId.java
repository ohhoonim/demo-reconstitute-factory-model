package dev.ohhoonim.post.model;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import dev.ohhoonim.components.model.unit.EntityId;

public record PostId(String postId) implements EntityId {

    public PostId {
        if ( postId == null || !Ulid.isValid(postId)) {
            throw new PostException("올바른 형식의 ID가 아닙니다.");
        }
    }

    @Override
    public String getRawValue() {
        return postId();
    }

    public static Creator<PostId> Creator = new Creator<PostId>() {

        @Override
        public PostId from(String value) {
            return new PostId(value);
        }

        @Override
        public PostId generate() {
            // auto increment 를 사용하는 경우라면 
            // 지원하지 않는다는 Exceptin을 뱉으면 된다.
            return new PostId(UlidCreator.getMonotonicUlid().toString());
        }
        
    };    

}