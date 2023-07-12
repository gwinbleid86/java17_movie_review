package kg.attractor.movie_review.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectorDto {
    private Long id;
    private String fullName;
}
