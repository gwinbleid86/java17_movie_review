package kg.attractor.movie_review.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class User {
    @Id
    private String email;

    private String username;

    private String password;

    private boolean enabled;

    private String resetPasswordToken;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer")
    private List<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Collection<Role> roles;
}
