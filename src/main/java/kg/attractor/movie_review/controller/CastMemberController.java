package kg.attractor.movie_review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cast_members")
public interface CastMemberController {

    @DeleteMapping("{id}")
    HttpStatus delete(@PathVariable Long id);
}
