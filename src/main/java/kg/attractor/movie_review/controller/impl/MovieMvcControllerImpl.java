package kg.attractor.movie_review.controller.impl;

import kg.attractor.movie_review.controller.MovieMvcController;
import kg.attractor.movie_review.dto.CastMemberDto;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.dto.MovieMvcDto;
import kg.attractor.movie_review.service.MovieService;
import kg.attractor.movie_review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieMvcControllerImpl implements MovieMvcController {
    private final MovieService movieService;
    private final ReviewService reviewService;

    @Override
    public String getMovies(Model model) {
        var movies = movieService.getMovies(0, 9, "id");
        model.addAttribute("movies", movies);
        return "movies/movies";
    }

    @Override
    public String getMovie(Long movieId, int page, int size, Model model) {
        model.addAttribute("movie", movieService.getMovieDtoById(movieId));
        model.addAttribute("reviews", reviewService.getReviewsByMovieId(movieId, "createTime", page, size));
        return "movies/movie_info";
    }

    @Override
    public String addMovie() {
        return "movies/add_movie";
    }

    @Override
    public String addMovie(String castMemberName, String castMemberRole, MovieMvcDto movieDto, Authentication auth) {
        movieService.saveMovie(
                MovieDto.builder()
                        .name(movieDto.getName())
                        .releaseYear(movieDto.getReleaseYear())
                        .description(movieDto.getDescription())
                        .director(DirectorDto.builder()
                                .fullName(movieDto.getDirector())
                                .build())
                        .castMembers(List.of(
                                CastMemberDto.builder()
                                        .fullName(castMemberName)
                                        .role(castMemberRole)
                                        .build()
                        ))
                        .build(),
                auth
        );

        return "redirect:/";
    }
}
