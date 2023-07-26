package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies") // http://localhost:8089/movies
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping // http://localhost:8089/movies
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/search/{name}") // http://localhost:8089/movies/search/test
    public ResponseEntity<?> findMovieByName(@PathVariable String name) {
        return movieService.getMovieByName(name);
    }

    @PostMapping("/add")
    public HttpStatus createMovie(@RequestBody MovieDto movieDto, Authentication auth) {
        movieService.saveMovie(movieDto, auth);
        return HttpStatus.OK;
    }

    @DeleteMapping("{movieId}")
    public HttpStatus delete(@PathVariable Long movieId) {
        movieService.delete(movieId);
        return HttpStatus.OK;
    }

    @GetMapping("{movieId}")
    public MovieDto getById(@PathVariable Long movieId) {
        return movieService.getMovieById(movieId);
    }

}
