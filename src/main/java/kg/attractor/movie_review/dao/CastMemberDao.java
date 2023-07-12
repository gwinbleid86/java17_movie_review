package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.CastMember;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CastMemberDao {
    private final JdbcTemplate jdbcTemplate;

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
}
