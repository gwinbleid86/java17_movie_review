package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.MovieImage;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Component
public class MovieImageDao extends BaseDao {

    MovieImageDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public void save(MovieImage movieImage) {
        String sql = "insert into movie_images (movie_id, file_name) " +
                "values ( ?, ? )";
        jdbcTemplate.update(sql, movieImage.getMovieId(), movieImage.getFileName());
    }

    public MovieImage getImageById(long imageId) {
        String sql = "select * from MOVIE_IMAGES where ID = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MovieImage.class), imageId));
    }

    @Override
    public Long save(Object obj) {
        jdbcTemplate.update(con -> {
            MovieImage mi = (MovieImage) obj;
            PreparedStatement ps = con.prepareStatement(
                    "insert into movie_images(movie_id, file_name) values (?, ?)",
                    new String[]{"id"}
            );
            ps.setLong(1, mi.getMovieId());
            ps.setString(2, mi.getFileName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "delete from movie_images where id = ?;",
                id
        );
    }

    public Optional<MovieImage> findImageByMovieId(Long movieId) {
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(
                                """
                                        select *
                                        from movie_images
                                        where movie_id = ?;
                                        """,
                                new BeanPropertyRowMapper<>(MovieImage.class),
                                movieId
                        )
                )
        );
    }
}
