package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.ReviewDto;
import kg.attractor.movie_review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> getReviews() {
        return reviewService.getReviews();
    }

    @PostMapping
    public HttpStatus addReview(@RequestBody ReviewDto reviewDto) {
        reviewService.addReview(reviewDto);
        return HttpStatus.OK;
    }
}
