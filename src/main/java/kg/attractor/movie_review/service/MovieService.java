package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.MovieDao;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.enums.SortMovieListStrategy;
import kg.attractor.movie_review.model.Movie;
import kg.attractor.movie_review.model.MovieCastMember;
import kg.attractor.movie_review.repository.MovieRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieService {
    MovieRepository repository;
    MovieDao movieDao;
    DirectorService directorService;
    CastMemberService castMemberService;
    MovieCastMemberService movieCastMemberService;

    public Page<MovieDto> getMovies(int page, int size, String sort) {
//        List<Movie> movies = movieDao.getMovies();
//        return toPage(movies, PageRequest.of(page, size), Sort.by(sort));
        var list = repository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        return toPage(list.getContent(), PageRequest.of(list.getNumber(), list.getSize(), list.getSort()));
    }

    private Page<MovieDto> toPage(List<Movie> movies, Pageable pageable) {
        var list = movies.stream()
                .map(this::makeMovieDtoFromMovie)
                .toList();
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<MovieDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

    private Page<MovieDto> toPage(List<Movie> movies, Pageable pageable, Sort sort) {
        var list = sortMovies(movies, sort.get()
                .map(Sort.Order::getProperty)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Sort property not found")));
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ?
                list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<MovieDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
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
        return new ResponseEntity<>(
                sortMovies(movies, sortedCriteria),
                HttpStatus.OK
        );
    }

    private List<MovieDto> sortMovies(List<Movie> movies, String sortedCriteria) {
        return SortMovieListStrategy.fromString(sortedCriteria).sortingMovies(movies).stream()
                .map(this::makeMovieDtoFromMovie)
                .toList();
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

    public MovieDto getMovieById(Long movieId) {
        var movie = movieDao.findMovieById(movieId).orElseThrow(() -> new NoSuchElementException("Movie not found"));
        var director = directorService.findDirectorById(movie.getDirectorId()).orElseThrow(() -> new NoSuchElementException("Director not found"));
        var castMembers = castMemberService.getCastMembersByMovieId(movie.getId());
        castMembers.forEach(e -> e.setRole(
                movieCastMemberService.findRoleByMovieIdAndCastMemberId(movie.getId(), e.getId())
        ));
        return MovieDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .description(movie.getDescription())
                .director(DirectorDto.builder()
                        .id(director.getId())
                        .fullName(director.getFullName())
                        .build())
                .castMembers(castMembers)
                .build();
    }

    public ResponseEntity<?> search(String movieId, String movieName, String castMemberName) {
        // movie_name, cast_member_name, movie_id
        if (movieId != null && !movieId.isBlank()) {
            return new ResponseEntity<>(getMovieById(Long.valueOf(movieId)), HttpStatus.OK);
        }

        if (castMemberName != null && !castMemberName.isBlank()) {
            return findMoviesByCastMemberName(castMemberName);
        }

        if (movieName != null && !movieName.isBlank()) {
            return getMovieByName(movieName);
        }

        throw new NoSuchElementException("Movie not found");
    }
}
