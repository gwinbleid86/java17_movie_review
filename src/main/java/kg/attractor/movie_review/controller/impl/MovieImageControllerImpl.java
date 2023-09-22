package kg.attractor.movie_review.controller.impl;

import kg.attractor.movie_review.controller.MovieImageController;
import kg.attractor.movie_review.dto.MovieImageDto;
import kg.attractor.movie_review.service.MovieImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieImageControllerImpl implements MovieImageController {
    private final MovieImageService movieImageService;

    @Override
    public ResponseEntity<?> downloadImage(long imageId) {
        return movieImageService.downloadImage(imageId);
    }

    @Override
    public HttpStatus uploadImage(MovieImageDto movieImageDto) {
        movieImageService.uploadImage(movieImageDto);
        return HttpStatus.OK;
    }

    @Override
    public ResponseEntity<?> getImageByMovie(Long movieId) {
        return movieImageService.getImageByMovieId(movieId);
    }
}
