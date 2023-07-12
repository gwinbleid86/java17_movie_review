package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.Director;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DirectorDao {
    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public Director findDirectorById(Long id) {
        String sql = "select * from DIRECTOR where ID = ?;";

        Optional<Director> mayBeDirector = Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Director.class), id)
        ));

        if (mayBeDirector.isEmpty()) {
            throw new Exception("Director not found");
        }

        return mayBeDirector.get();
    }
}
