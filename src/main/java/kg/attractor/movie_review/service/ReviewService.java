package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dto.ReviewDto;
import kg.attractor.movie_review.model.Review;
import kg.attractor.movie_review.repository.MovieRepository;
import kg.attractor.movie_review.repository.ReviewRepository;
import kg.attractor.movie_review.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss");

    public void addReview(ReviewDto reviewDto, Authentication auth) {
        User user = (User) auth.getPrincipal();
        log.info("User: {}", user);
        reviewRepository.save(Review.builder()
                .rating(reviewDto.getRating())
                .comment(reviewDto.getComment())
                .movie(movieRepository.findById(reviewDto.getMovieId()).orElseThrow(() -> new NoSuchElementException("Movie not found")))
                .reviewer(userRepository.findById(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .build());
    }

    public List<ReviewDto> getReviews() {
        return reviewRepository.findAll().stream()
                .map(e -> ReviewDto.builder()
                        .comment(e.getComment())
                        .reviewer(e.getReviewer().getEmail())
                        .rating(e.getRating())
                        .movieId(e.getMovie().getId())
                        .createTime(e.getCreateTime().format(FORMATTER))
                        .build())
                .toList();
    }

    public List<ReviewDto> getReviewsByMovieId(Long movieId) {
        List<Review> reviews = reviewRepository.findAllByMovie_Id(movieId);
        return reviews.stream()
                .map(e -> ReviewDto.builder()
                        .rating(e.getRating())
                        .comment(e.getComment())
                        .reviewer(e.getReviewer().getEmail())
                        .createTime(e.getCreateTime().format(FORMATTER))
                        .build())
                .toList();
    }
}
