package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.ReviewDao;
import kg.attractor.movie_review.dto.ReviewDto;
import kg.attractor.movie_review.model.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;

    public void addReview(ReviewDto reviewDto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        log.info("User: {}", user);
        reviewDao.save(Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .movieId(reviewDto.getMovieId())
                .reviewer(user.getUsername())
                .build());
    }

    public List<ReviewDto> getReviews() {
        return reviewDao.getAllReviews().stream()
                .map(e -> ReviewDto.builder()
                        .comment(e.getComment())
                        .reviewer(e.getReviewer())
                        .rating(e.getRating())
                        .movieId(e.getMovieId())
                        .build())
                .toList();
    }

    public List<ReviewDto> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewDao.getReviewsByMovieId(movieId);
        return reviews.stream()
                .map(e -> ReviewDto.builder()
                        .rating(e.getRating())
                        .comment(e.getComment())
                        .reviewer(e.getReviewer())
                        .build())
                .toList();
    }
}
