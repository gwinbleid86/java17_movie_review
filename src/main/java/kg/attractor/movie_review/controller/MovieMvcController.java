package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.MovieMvcDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public interface MovieMvcController {

    @GetMapping
    String getMovies(Model model);

    @GetMapping("/{movieId}")
    String getMovie(
            @PathVariable Long movieId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            Model model
    );

    @GetMapping("add")
    public String addMovie();

    @PostMapping("add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    String addMovie(
            @RequestParam(name = "cast_member_name") String castMemberName,
            @RequestParam(name = "cast_member_role") String castMemberRole,
            MovieMvcDto movieDto,
            Authentication auth
    );
}
