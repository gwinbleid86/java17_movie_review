package kg.attractor.movie_review.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Movie {
    private Long id;
    private String name;
    private Integer releaseYear;
    private String description;
    private Long directorId;
    private List<Long> castMembersId;

}
