package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.Director;
import lombok.SneakyThrows;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;

@Component
public class DirectorDao extends BaseDao {

    public DirectorDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @SneakyThrows
    public Optional<Director> findDirectorById(Long id) {
        String sql = "select * from DIRECTOR where ID = ?;";

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Director.class), id)
        ));
    }

    @Override
    public Long save(Object obj) {
        String sql = "insert into director(fullname) values(?)";
        jdbcTemplate.update(con -> {
            Director d = (Director) obj;
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, d.getFullName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from director where id = ?;", id);
    }

    public Optional<Director> findDirectorByName(String fullName) {
        return Optional.ofNullable(DataAccessUtils.singleResult(jdbcTemplate.query(
                "select * from director where fullname like ?;",
                new BeanPropertyRowMapper<>(Director.class),
                "%" + fullName + "%"
        )));
    }
}
