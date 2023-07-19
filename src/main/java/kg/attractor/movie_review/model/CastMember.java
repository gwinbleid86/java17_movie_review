package kg.attractor.movie_review.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CastMember {
    private Long id;
    private String fullName;
}
