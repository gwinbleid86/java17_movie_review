package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.Review;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class ReviewDao extends BaseDao {

    ReviewDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public void save(Review review) {
        String sql = "insert into review (rating, comment, reviewer, movie_id) " +
                "values ( ?, ?, ?, ? )";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setString(3, review.getReviewer());
            ps.setLong(4, review.getMovieId());
            return ps;
        });
    }

    public List<Review> getAllReviews() {
        String sql = "select * from review;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Review.class));
    }

    @Override
    public Long save(Object obj) {
        jdbcTemplate.update(con -> {
            Review r = (Review) obj;
            PreparedStatement ps = con.prepareStatement(
                    "insert into review(rating, comment, reviewer, movie_id) values (?, ?, ?, ?);",
                    new String[]{"id"}
            );
            ps.setInt(1, r.getRating());
            ps.setString(2, r.getComment());
            ps.setString(3, r.getReviewer());
            ps.setLong(4, r.getMovieId());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "delete from review where id = ?;",
                id
        );
    }
}
