package kg.attractor.movie_review.controller;

import kg.attractor.movie_review.dto.MovieImageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public interface MovieImageController {

    @GetMapping("/download/{imageId}")
    ResponseEntity<?> downloadImage(@PathVariable long imageId);

    @PostMapping("/upload")
    HttpStatus uploadImage(MovieImageDto movieImageDto);

    @GetMapping("{movieId}")
    ResponseEntity<?> getImageByMovie(@PathVariable Long movieId);
}
