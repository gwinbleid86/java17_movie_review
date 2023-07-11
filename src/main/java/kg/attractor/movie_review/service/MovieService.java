package kg.attractor.movie_review.service;

import kg.attractor.movie_review.model.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    public Movie getMovieById(long movieId) {
        return Movie.builder()
                .name("Good omens")
                .year(2019)
                .description("TV Series")
                .build();
    }

    public void createMovie(Movie movie) {
        System.out.println(movie);
    }
}
