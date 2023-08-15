package kg.attractor.movie_review.model;

import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createTime;
}
