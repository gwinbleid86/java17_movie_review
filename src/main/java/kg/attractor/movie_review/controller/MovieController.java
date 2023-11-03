package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.MovieDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies") // http://localhost:8089/movies
public interface MovieController {

    @GetMapping
        // http://localhost:8089/movies
    Page<MovieDto> getMovies(Integer page, Integer size, String sort);

    @GetMapping("/sort/{sortedCriteria}")
        // http://localhost:8089/movies/sort/by_name
    ResponseEntity<?> sortMovies(@PathVariable String sortedCriteria);

    @GetMapping(value = "/search")
        // http://localhost:8089/movies/search?cast_member_name=Cast
    ResponseEntity<?> findMoviesByCastMemberName(
            @RequestParam(name = "movie_id", required = false) String movieId,
            @RequestParam(name = "movie_name", required = false) String movieName,
            @RequestParam(name = "cast_member_name", required = false) String castMemberName
    );

    @PostMapping("/add")
    HttpStatus createMovie(@RequestBody MovieDto movieDto, Authentication auth);

    @DeleteMapping("{movieId}")
    HttpStatus delete(@PathVariable Long movieId);
}
