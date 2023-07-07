package kg.attractor.movie_review.dao;

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
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(int id) {
        String sql = "select * from users where id = ?";

        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }

    public Optional<User> getOptionalUserById(int id) {
        String sql = "select * from users where id = ?";

        User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserById2(int userId) {
        String sql = "select * from users where id = ?";
//        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId);

        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new BeanPropertyRowMapper<>(User.class),
                        userId
                )
        ));

//        try {
//            User user = DataAccessUtils.singleResult(users);
//            return Optional.ofNullable(user);
//        } catch (IncorrectResultSizeDataAccessException e) {
//            return Optional.empty();
//        }

    }


    public void createTable() {
        String sql = "create table users\n" +
                "(\n" +
                "    id       int identity primary key,\n" +
                "    name     varchar(50),\n" +
                "    password varchar(50)\n" +
                ");";
        jdbcTemplate.update(sql);
    }
//
//    public List<User> getAllUsers() {
//        String sql = "select * from users where age > :age";
//        Map<String, Object> params = new HashMap<>();
//        params.put("age", 18);
//        return namedParameterJdbcTemplate.query(sql, params, new UserMapper());
//    }
//
//    public void insertUser() {
//        String sql = "insert into users (name, password) values(:name, :password)";
////        Map<String, Object> params = new HashMap<>();
////        params.put("name", "Test");
////        params.put("password", "qwe");
//        SqlParameterSource params = new MapSqlParameterSource()
//                .addValue("name", "Test")
//                .addValue("password", "qwerty");
//        namedParameterJdbcTemplate.update(sql, params);
//    }
//
//    public int addUser(String name, String passwd) {
//        String sql = "insert into users (name, password) values(?, ?)";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(conn -> {
//            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, name);
//            ps.setString(2, passwd);
//            return ps;
//        }, keyHolder);
//
//
//        String sql2 = "select case\n" +
//                "           when exists(\n" +
//                "                   select * from USERS where id = 22\n" +
//                "               )\n" +
//                "               then true\n" +
//                "           else false\n" +
//                "           end;";
//        jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Boolean.class));
//
//        return keyHolder.getKey();
//    }
}
