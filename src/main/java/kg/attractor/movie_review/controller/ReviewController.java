package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.ReviewDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public interface ReviewController {

    @GetMapping
    List<ReviewDto> getReviews();

    @GetMapping("{movieId}")
    List<ReviewDto> getReviewsByMovie(@PathVariable Long movieId);

    @PostMapping
    HttpStatus addReview(@RequestBody ReviewDto reviewDto, Authentication auth);
}
