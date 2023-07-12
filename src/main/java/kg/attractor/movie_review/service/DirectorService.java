package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.DirectorDao;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorDao directorDao;

    public DirectorDto findDirectorById(Long id) {
        Director director = directorDao.findDirectorById(id);
        return DirectorDto.builder()
                .id(director.getId())
                .fullName(director.getFullName())
                .build();
    }
}
