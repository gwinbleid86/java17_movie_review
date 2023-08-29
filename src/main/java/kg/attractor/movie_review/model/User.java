package kg.attractor.movie_review.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_table")
public class User {
    @Id
    private String email;

    private String username;

    private String password;

    private boolean enabled;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user_table")
//    private List<Review> reviews;
}
