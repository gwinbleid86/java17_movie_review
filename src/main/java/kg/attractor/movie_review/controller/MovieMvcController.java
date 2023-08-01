package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.CastMemberDto;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.dto.MovieMvcDto;
import kg.attractor.movie_review.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieMvcController {
    private final MovieService service;

    @GetMapping
    public String getMovies(Model model) {
        var movies = service.getMovies(0, 9, "id");
        model.addAttribute("movies", movies);
        return "movies/movies";
    }

    @GetMapping("/{movieId}")
    public String getMovie(@PathVariable Long movieId, Model model) {
        model.addAttribute("movie", service.getMovieById(movieId));
        return "movies/movie_info";
    }

    @GetMapping("add")
    public String addMovie() {
        return "movies/add_movie";
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String addMovie(
            @RequestParam(name = "cast_member_name") String castMemberName,
            @RequestParam(name = "cast_member_role") String castMemberRole,
            MovieMvcDto movieDto,
            Authentication auth
    ) {
        service.saveMovie(
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
