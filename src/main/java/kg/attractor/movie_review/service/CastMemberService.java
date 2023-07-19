package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.CastMemberDao;
import kg.attractor.movie_review.dto.CastMemberDto;
import kg.attractor.movie_review.model.CastMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CastMemberService {
    private final CastMemberDao castMemberDao;

    public List<CastMemberDto> getCastMembersByMovieId(Long movieId) {
        List<CastMember> members = castMemberDao.findCastMemberByMovieId(movieId);
        return members.stream()
                .map(e -> CastMemberDto.builder()
                        .id(e.getId())
                        .fullName(e.getFullName())
                        .build()
                )
                .toList();
    }

    public Optional<CastMember> findCastMemberByName(String name) {
        return castMemberDao.findCastMemberByName(name);
    }

    public void delete(Long id) {
        castMemberDao.delete(id);
    }

    public long save(CastMemberDto castMemberDto) {
        return castMemberDao.save(CastMember.builder().fullName(castMemberDto.getFullName()).build());
    }
}
