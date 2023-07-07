package kg.attractor.movie_review.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Movie {
    private String name;
    private Integer year;
    private String description;

}
