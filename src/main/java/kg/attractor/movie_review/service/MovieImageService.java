package kg.attractor.movie_review.service;

import kg.attractor.movie_review.dao.MovieImageDao;
import kg.attractor.movie_review.dto.MovieImageDto;
import kg.attractor.movie_review.model.MovieImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieImageService {
    private static final String SUB_DIR = "images";
    private final FileService fileService;
    private final MovieImageDao movieImageDao;

    public void uploadImage(MovieImageDto movieImageDto) {
        String fileName = fileService.saveUploadedFile(movieImageDto.getFile(), SUB_DIR);
        MovieImage mi = MovieImage.builder()
                .movieId(movieImageDto.getMovieId())
                .fileName(fileName)
                .build();
        movieImageDao.save(mi);
    }

    public ResponseEntity<?> downloadImage(long imageId) {
        MovieImage mi = movieImageDao.getImageById(imageId);
        String fileName = mi.getFileName();
        return fileService.getOutputFile(fileName, SUB_DIR, MediaType.IMAGE_JPEG);
    }
}
