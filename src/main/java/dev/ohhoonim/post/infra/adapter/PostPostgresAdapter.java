package dev.ohhoonim.post.infra.adapter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import dev.ohhoonim.post.activity.out.PostArFactory;
import dev.ohhoonim.post.activity.out.PostRepository;
import dev.ohhoonim.post.activity.out.ReplyRepository;
import dev.ohhoonim.post.model.PostComponent;
import dev.ohhoonim.post.model.Reply;

@Component
public class PostPostgresAdapter implements PostRepository, ReplyRepository {

    private final JdbcClient jdbcClient;
    private final PostArFactory postArFactory;

    public PostPostgresAdapter(JdbcClient jdbcClient, PostArFactory postArFactory) {
        this.jdbcClient = jdbcClient;
        this.postArFactory = postArFactory;
    }

    @Override
    public List<Map<String, Object>> findAll(List<Class<? extends PostComponent>> columns) {
        List<String> cols = postArFactory.resolveRequiredColumns(columns);
        var sql = """
                select %s from tb_post
                """.formatted(cols.stream().collect(Collectors.joining(", ")));
        return jdbcClient.sql(sql).query(new ColumnMapRowMapper()).list();

    }

    @Override
    public Optional<Map<String, Object>> findById(String postId,
            List<Class<? extends PostComponent>> columns) {
        List<String> cols = postArFactory.resolveRequiredColumns(columns);
        var sql = """
                select %s from tb_post where post_id = :postId
                """.formatted(cols.stream().collect(Collectors.joining(", ")));
        return jdbcClient.sql(sql).param("postId", postId).query(new ColumnMapRowMapper())
                .optional();
    }



    @Override
    public List<Reply> repliesByPostId(String postId) {
        var sql = """
                select reply_id as replyId
                , post_id as postId
                , contents as contents
                , created_at as createdAt
                , created_by as createdBy
                from tb_comment
                """;
        return jdbcClient.sql(sql).query(Reply.class).list();
    }

    @Override
    public void saveReply(Reply reply) {
        var sql = """
                update tb_comment
                set
                     contents = :contents,
                     created_at = now(),
                     created_by = :createdBy
                where reply_id = :replyId
                 """;
        jdbcClient.sql(sql).params("contents", reply.contents())
                .params("createdBy", reply.createdBy()).params("replyId", reply.replyId()).update();
    }

    @Override
    public Long addReply(Reply reply) {
        var keyHolder = new GeneratedKeyHolder();
        var sql = """
                insert into tb_comment (post_id, contents, created_at, created_by)
                values(:postId, :contents, now(), :createdBy)
                    """;
        jdbcClient.sql(sql).param("postId", reply.postId().toValue())
                .param("contents", reply.contents()).param("createdBy", reply.createdBy())
                .update(keyHolder);

        return (Long)keyHolder.getKeys().get("reply_id");
    }

}
