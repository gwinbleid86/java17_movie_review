package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.MovieDao;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.enums.SortMovieListStrategy;
import kg.attractor.movie_review.model.Movie;
import kg.attractor.movie_review.model.MovieCastMember;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService {
    MovieDao movieDao;
    DirectorService directorService;
    CastMemberService castMemberService;
    MovieCastMemberService movieCastMemberService;

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
        var director = directorService.findDirectorById(movie.getDirectorId());
        var directorDto = new DirectorDto();
        if (director.isPresent()) {
            directorDto = DirectorDto.builder()
                    .id(director.get().getId())
                    .fullName(director.get().getFullName())
                    .build();
        }
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .description(movie.getDescription())
                .director(directorDto)
                .castMembers(castMemberService.getCastMembersByMovieId(movie.getId()))
                .build();
    }

    public void saveMovie(MovieDto movieDto, Authentication auth) {
        User u = (User) auth.getPrincipal();
        log.info(u.getUsername());

        var mayBeDirector = directorService.findDirectorByName(movieDto.getDirector().getFullName());
        long directorId;
        if (mayBeDirector.isPresent()) {
            directorId = mayBeDirector.get().getId();
        } else {
            directorId = directorService.save(movieDto.getDirector());
        }

        var mayBeMovie = movieDao.findMovieByName(movieDto.getName());
        long movieId;
        if (mayBeMovie.isPresent()) {
            movieId = mayBeMovie.get().getId();
        } else {
            movieId = movieDao.save(Movie.builder()
                    .name(movieDto.getName())
                    .releaseYear(movieDto.getReleaseYear())
                    .description(movieDto.getDescription())
                    .directorId(directorId)
                    .build());
        }

        movieDto.getCastMembers().forEach(e -> {
            var mayBeCastMember = castMemberService.findCastMemberByName(e.getFullName());
            long castMemberId;
            if (mayBeCastMember.isPresent()) {
                castMemberId = mayBeCastMember.get().getId();
            } else {
                castMemberId = castMemberService.save(e);
            }

            movieCastMemberService.save(MovieCastMember.builder()
                    .movieId(movieId)
                    .castMemberId(castMemberId)
                    .role(e.getRole())
                    .build());
        });
    }

    public void delete(Long id) {
        movieDao.delete(id);
    }

}
