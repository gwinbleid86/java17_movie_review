package kg.attractor.movie_review.controller.impl;

import kg.attractor.movie_review.controller.CastMemberController;
import kg.attractor.movie_review.service.CastMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CastMemberControllerImpl implements CastMemberController {
    CastMemberService castMemberService;

    @Override
    public HttpStatus delete(@PathVariable Long id) {
        castMemberService.delete(id);
        return HttpStatus.OK;
    }
}
