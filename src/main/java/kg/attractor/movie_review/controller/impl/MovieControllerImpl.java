package kg.attractor.movie_review.controller.impl;

import kg.attractor.movie_review.controller.MovieController;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RequiredArgsConstructor
public class MovieControllerImpl implements MovieController {
    private final MovieService movieService;

    @Override
    public Page<MovieDto> getMovies(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sort
    ) {
        return movieService.getMovies(page, size, sort);
    }

    @Override
    public ResponseEntity<?> sortMovies(String sortedCriteria) {
        return movieService.sortedListMovies(sortedCriteria);
    }

    @Override
    public ResponseEntity<?> findMoviesByCastMemberName(
            @RequestParam(name = "movie_id", required = false) String movieId,
            @RequestParam(name = "movie_name", required = false) String movieName,
            @RequestParam(name = "cast_member_name", required = false) String castMemberName
    ) {
        return movieService.search(movieId, movieName, castMemberName);
    }

    @Override
    public HttpStatus createMovie(@RequestBody MovieDto movieDto, Authentication auth) {
        movieService.saveMovie(movieDto, auth);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus delete(@PathVariable Long movieId) {
        movieService.delete(movieId);
        return HttpStatus.OK;
    }

}
