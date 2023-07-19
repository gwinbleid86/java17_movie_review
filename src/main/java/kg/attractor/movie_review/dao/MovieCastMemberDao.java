package kg.attractor.movie_review.dao;

import kg.attractor.movie_review.model.MovieCastMember;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Optional;

@Component
public class MovieCastMemberDao extends BaseDao {

    MovieCastMemberDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    public Optional<String> getRoleFromMovieIdAndCastMemberId(Long movieId, Long castMemberId) {
        String sql = "select role\n" +
                "from movie_cast_member\n" +
                "where MOVIE_ID = ?\n" +
                "  and CAST_MEMBER_ID = ?;";
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(String.class),
                movieId,
                castMemberId
        ));
    }

    @Override
    public Long save(Object obj) {
        String sql = "insert into movie_cast_member(movie_id, cast_member_id, role) values (?, ?, ?)";
        return (long) jdbcTemplate.update(con -> {
            MovieCastMember mcm = (MovieCastMember) obj;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, mcm.getMovieId());
            ps.setLong(2, mcm.getCastMemberId());
            ps.setString(3, mcm.getRole());
            return ps;
        });
    }

    @Override
    public void delete(Long id) {
    }
}
