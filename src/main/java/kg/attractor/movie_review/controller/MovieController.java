package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.model.Movie;
import kg.attractor.movie_review.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable long movieId) {
        return new ResponseEntity<>(movieService.getMovieById(movieId), HttpStatus.OK);
    }

    @PostMapping("/movies")
    public ResponseEntity<Void> addMovie(@RequestBody Movie movie) {
        movieService.createMovie(movie);
        return ResponseEntity.ok().build();
    }

}
