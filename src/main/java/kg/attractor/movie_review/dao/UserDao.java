package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao extends BaseDao {


    UserDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<User> getAllUsers() {
        String sql = "select * from user_table";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public Long save(Object obj) {
        User u = (User) obj;
        long rows = jdbcTemplate.update(
                "insert into user_table(email, username, password, enabled) values (?, ?, ?, ?)",
                u.getEmail(),
                u.getUsername(),
                u.getPassword(),
                u.isEnabled()
        );
        setRoleToNewUser(u.getEmail());
        return rows;
    }

    @Override
    public void delete(Long id) {
    }

    public void setRoleToNewUser(String email) {
        jdbcTemplate.update("""
                        insert into roles(authority_id, user_email) values (?, ?);
                        """,
                2, email);
    }
}
