package kg.attractor.movie_review.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class BaseDao {
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    KeyHolder keyHolder;

    protected BaseDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.keyHolder = new GeneratedKeyHolder();
    }

    public abstract Long save(Object obj);

    public abstract void delete(Long id);
}
