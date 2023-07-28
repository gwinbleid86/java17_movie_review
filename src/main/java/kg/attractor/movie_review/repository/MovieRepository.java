package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
}
