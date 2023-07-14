package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies") // http://localhost:8089/movies
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    //    @GetMapping("/movies/{movieId}")
//    public ResponseEntity<Movie> getMovie(@PathVariable long movieId) {
//        return new ResponseEntity<>(movieService.getMovieById(movieId), HttpStatus.OK);
//    }
//
//    @PostMapping("/movies")
//    public ResponseEntity<Void> addMovie(@RequestBody Movie movie) {
//        movieService.createMovie(movie);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping // http://localhost:8089/movies
    public List<MovieDto> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping
    @RequestMapping("/search/{name}") // http://localhost:8089/movies/search/test
    public ResponseEntity<?> findMovieByName(@PathVariable String name) {
        return movieService.getMovieByName(name);
    }

    @GetMapping("/sort/{sortedCriteria}") // http://localhost:8089/movies/sort/by_name
    public ResponseEntity<?> sortMovies(@PathVariable String sortedCriteria) {
        return movieService.sortedListMovies(sortedCriteria);
    }

    @GetMapping("/search") // http://localhost:8089/movies/search?cast_member_name=Cast
    public ResponseEntity<?> findMoviesByCastMemberName(@RequestParam(value = "cast_member_name") String name) {
        return movieService.findMoviesByCastMemberName(name);
    }
}
