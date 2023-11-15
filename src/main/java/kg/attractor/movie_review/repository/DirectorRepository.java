package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.Director;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {
    Optional<Director> findByFullName(String fullName);
}
