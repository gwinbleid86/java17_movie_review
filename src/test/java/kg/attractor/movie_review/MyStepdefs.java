package kg.attractor.movie_review;

import io.cucumber.java.Before;
import io.cucumber.java.ru.Когда;
import io.cucumber.spring.CucumberContextConfiguration;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.service.MovieService;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@CucumberContextConfiguration
public class MyStepdefs {

    @Autowired
    MovieService movieService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Когда("я запрашиваю фильм с id {long}")
    public void requestMovieForId(long movieId) {
        // Вызываем метод для тестирования
        MovieDto movieDto = movieService.getMovieDtoById(movieId);

        // Добавьте проверки
        assertEquals(movieId, movieDto.getId());
    }
}
