package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.MovieCastMemberDao;
import kg.attractor.movie_review.model.MovieCastMember;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieCastMemberService {
    MovieCastMemberDao movieCastMemberDao;

    public long save(MovieCastMember movieCastMember) {
        return movieCastMemberDao.save(movieCastMember);
    }
}
