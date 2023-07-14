package kg.attractor.movie_review.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MovieImage {
    private long id;
    private long movieId;
    private String fileName;
}
