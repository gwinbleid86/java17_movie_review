package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Movie> getMovies() {
        String sql = "select * from MOVIE;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class));
    }

    public Optional<Movie> findMovieByName(String name) {
        String sql = "select * from MOVIE\n" +
                "where NAME like ?;";
        name = "%" + name + "%";
        var movie = Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), name)
        ));
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), name)
        ));
    }

    public List<Movie> findMoviesByCastMemberId(Long id) {
        String sql = "select m.id,\n" +
                "       m.name,\n" +
                "       m.release_year,\n" +
                "       m.description,\n" +
                "       m.director_id\n" +
                "from MOVIE m,\n" +
                "     MOVIE_CAST_MEMBER mcm\n" +
                "where mcm.CAST_MEMBER_ID = ?\n" +
                "  and m.ID = mcm.MOVIE_ID;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), id);
    }
}
