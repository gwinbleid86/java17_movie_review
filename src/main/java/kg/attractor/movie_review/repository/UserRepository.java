package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
