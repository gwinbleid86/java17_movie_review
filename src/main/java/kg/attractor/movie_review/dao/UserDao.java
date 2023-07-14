package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from user_table";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public void createUser(User user) {
        String sql = "insert into user_table(email, username, password)\n" +
                "values(?, ?, ?);";

        jdbcTemplate.update(sql, user.getEmail(), user.getName(), user.getPasswd());
    }
}
