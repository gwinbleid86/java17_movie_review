package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.ReviewDao;
import kg.attractor.movie_review.dto.ReviewDto;
import kg.attractor.movie_review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewDao reviewDao;

    public void addReview(ReviewDto reviewDto) {
        reviewDao.save(Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .movieId(reviewDto.getMovieId())
                .reviewer(reviewDto.getReviewer())
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
}
