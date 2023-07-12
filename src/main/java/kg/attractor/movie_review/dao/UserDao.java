package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.common.UserMapper;
import kg.attractor.movie_review.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<User> getAllUsers() {
        String sql = "select * from user_table";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(int id) {
        String sql = "select * from user_table where id = ?";

        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }

    public Optional<User> getOptionalUserById(int id) {
        String sql = "select * from user_table where id = ?";

        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserById2(int userId) {
        String sql = "select * from user_table where id = ?";
//        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId);

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(User.class),
                        userId
                )
        ));

    }


    public void createUser(User user) {
        String sql = "insert into user_table(username, password)\n" +
                "values(?, ?);";

        jdbcTemplate.update(sql, user.getName(), user.getPasswd());
    }
}
