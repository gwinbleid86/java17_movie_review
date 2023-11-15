package kg.attractor.movie_review.repository;

import kg.attractor.movie_review.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> getByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}
