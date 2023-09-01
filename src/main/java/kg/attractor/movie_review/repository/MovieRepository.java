package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>, CrudRepository<Movie, Long> {
    @Query("select m from Movie as m where m.name = :name")
    Optional<Movie> findMovieByName(String name);

    @Query("""
            select m.id,
                   m.name,
                   m.releaseYear,
                   m.description,
                   m.director.id
            from Movie m,
                 MovieCastMember mcm
            where mcm.castMember.id = :castMemberId
              and m.id = mcm.movie.id
            """)
    List<Movie> findByMovieCastMembersCastMemberId(Long castMemberId);
}
