package kg.attractor.movie_review.enums;

import kg.attractor.movie_review.model.Movie;

import java.util.Comparator;
import java.util.List;

public enum SortMovieListStrategy {
    BY_NAME("by_name") {
        @Override
        public List<Movie> sortingMovies(List<Movie> movies) {
            movies.sort(Comparator.comparing(Movie::getName));
            return movies;
        }
    },
    BY_RELEASE_YEAR("by_release_year") {
        @Override
        public List<Movie> sortingMovies(List<Movie> movies) {
            movies.sort(Comparator.comparing(Movie::getReleaseYear));
            return movies;
        }
    },
    BY_DIRECTOR_NAME("by_director") {
        @Override
        public List<Movie> sortingMovies(List<Movie> movies) {
            movies.sort(Comparator.comparing(Movie::getDirectorId));
            return movies;
        }
    };

    private String value;

    SortMovieListStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortMovieListStrategy fromString(String sortCriteria) throws Exception {
        for (var e : SortMovieListStrategy.values()) {
            if (e.value.equalsIgnoreCase(sortCriteria)) {
                return e;
            }
        }
        throw new Exception("Sorted criteria not found");
    }

    public abstract List<Movie> sortingMovies(List<Movie> movies);
}
