package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Long>, JpaRepository<Review, Long> {
    List<Review> findAllByMovie_Id(Long movieId);
}
