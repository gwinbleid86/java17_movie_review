package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.CastMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CastMemberRepository extends CrudRepository<CastMember, Long> {
    @Query("""
            select cm
            from CastMember cm,
                 MovieCastMember mcm,
                 Movie m
            where m.id = :movieId
              and mcm.movie.id = m.id
              and cm.id = mcm.castMember.id
            """)
    List<CastMember> findByMovieId(Long movieId);

    Optional<CastMember> findByFullName(String name);
}
