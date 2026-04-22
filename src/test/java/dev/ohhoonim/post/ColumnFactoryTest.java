package dev.ohhoonim.post;

import org.junit.jupiter.api.Test;
import com.github.f4b6a3.ulid.UlidCreator;

public class ColumnFactoryTest {
    
    @Test
    void generatePostId() {
        System.out.println(UlidCreator.getMonotonicUlid().toString());
        System.out.println(UlidCreator.getMonotonicUlid().toString());
        System.out.println(UlidCreator.getMonotonicUlid().toString());
    }

// 01KPSRPEESJXJTVWFC6VN7MSAE
// 01KPSRPEESJXJTVWFC6VN7MSAF
// 01KPSRPEESJXJTVWFC6VN7MSAG
}
