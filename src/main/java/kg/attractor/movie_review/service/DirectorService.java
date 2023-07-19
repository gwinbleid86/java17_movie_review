package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.DirectorDao;
import kg.attractor.movie_review.dto.DirectorDto;
import kg.attractor.movie_review.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorDao directorDao;

    public Optional<Director> findDirectorById(Long id) {
        return directorDao.findDirectorById(id);
    }

    public long save(DirectorDto director) {
        return directorDao.save(Director.builder()
                .fullName(director.getFullName())
                .build());
    }

    public Optional<Director> findDirectorByName(String fullName) {
        return directorDao.findDirectorByName(fullName);
    }
}
