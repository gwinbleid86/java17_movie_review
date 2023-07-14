package kg.attractor.movie_review.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {
    private long id;
    private int rating;
    private String comment;
    private String reviewer;
    private long movieId;
}
