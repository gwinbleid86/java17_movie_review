package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.MovieDao;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.enums.SortMovieListStrategy;
import kg.attractor.movie_review.model.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieDao movieDao;
    private final DirectorService directorService;
    private final CastMemberService castMemberService;

    public List<MovieDto> getMovies() {
        List<Movie> movies = movieDao.getMovies();
        return movies.stream()
                .map(this::makeMovieDtoFromMovie)
                .toList();
    }

    public ResponseEntity<?> getMovieByName(String name) {
        Optional<Movie> mayBeMovie = movieDao.findMovieByName(name);
        if (mayBeMovie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(makeMovieDtoFromMovie(mayBeMovie.get()), HttpStatus.OK);
    }

    public ResponseEntity<?> sortedListMovies(String sortedCriteria) {
        List<Movie> movies = movieDao.getMovies();
        try {
            var sortedMovies = SortMovieListStrategy.fromString(sortedCriteria).sortingMovies(movies);
            return new ResponseEntity<>(
                    sortedMovies.stream()
                            .map(this::makeMovieDtoFromMovie)
                            .toList(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> findMoviesByCastMemberName(String name) {
        var mayBeMember = castMemberService.findCastMemberByName(name);
        if (mayBeMember.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var movies = movieDao.findMoviesByCastMemberId(mayBeMember.get().getId());

        return new ResponseEntity<>(
                movies.stream()
                        .map(this::makeMovieDtoFromMovie)
                        .toList(),
                HttpStatus.OK
        );
    }


    private MovieDto makeMovieDtoFromMovie(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .description(movie.getDescription())
                .director(directorService.findDirectorById(movie.getDirectorId()))
                .castMembers(castMemberService.getCastMembersByMovieId(movie.getId()))
                .build();
    }

}