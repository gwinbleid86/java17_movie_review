package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewDao {
    private final JdbcTemplate jdbcTemplate;

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
}
