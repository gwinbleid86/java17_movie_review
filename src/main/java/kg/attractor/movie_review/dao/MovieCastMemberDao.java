package kg.attractor.movie_review.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieCastMemberDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<String> getRoleFromMovieIdAndCastMemberId(Long movieId, Long castMemberId) {
        String sql = "select role\n" +
                "from movie_cast_member\n" +
                "where MOVIE_ID = ?\n" +
                "  and CAST_MEMBER_ID = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(String.class),
                movieId,
                castMemberId
        ));
    }
}
