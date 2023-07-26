package kg.attractor.movie_review.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private String email;
    private String username;
    private String password;
    private boolean enabled;
}
