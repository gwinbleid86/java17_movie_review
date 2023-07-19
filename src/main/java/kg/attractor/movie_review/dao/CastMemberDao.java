package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.CastMember;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CastMemberDao extends BaseDao {

    public CastMemberDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public List<CastMember> findCastMemberByMovieId(Long id) {
        String sql = "select\n" +
                "    cm.id,\n" +
                "    cm.fullname\n" +
                "from CAST_MEMBER cm,\n" +
                "     MOVIE_CAST_MEMBER mcm,\n" +
                "     MOVIE m\n" +
                "where m.id = ?\n" +
                "and mcm.MOVIE_ID = m.ID\n" +
                "and cm.ID = mcm.CAST_MEMBER_ID;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CastMember.class), id);
    }

    public Optional<CastMember> findCastMemberByName(String name) {
        String sql = "select *\n" +
                "from CAST_MEMBER\n" +
                "where FULLNAME like ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CastMember.class), "%" + name + "%")
        ));
    }

    @Override
    public Long save(Object obj) {
        CastMember cm = (CastMember) obj;
        String sql = "insert into cast_member(fullname) values(?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, cm.getFullName());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from cast_member where id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
