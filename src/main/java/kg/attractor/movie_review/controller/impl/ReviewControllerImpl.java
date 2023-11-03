package kg.attractor.movie_review.controller.impl;

import kg.attractor.movie_review.controller.ReviewController;
import kg.attractor.movie_review.dto.ReviewDto;
import kg.attractor.movie_review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {
    private final ReviewService reviewService;

    @Override
    public List<ReviewDto> getReviews() {
        return reviewService.getReviews();
    }

    @Override
    public List<ReviewDto> getReviewsByMovie(Long movieId) {
        return reviewService.getReviewsByMovieId(movieId, "createTime", 0, 200);
    }

    @Override
    public HttpStatus addReview(ReviewDto reviewDto, Authentication auth) {
        reviewService.addReview(reviewDto, auth);
        return HttpStatus.OK;
    }
}
