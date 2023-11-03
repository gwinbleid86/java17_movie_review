package stepdefs;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import kg.attractor.movie_review.dto.MovieDto;
import kg.attractor.movie_review.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StepDefinitions {
    private MovieDto movieDto;
    @Autowired
    private MovieService movieService;

    @Когда("я запрашиваю фильм с id {long}")
    public void requestMovieForId(long movieId) {
        // Вызываем метод для тестирования
        movieDto = movieService.getMovieDtoById(movieId);

        // Добавьте проверки
        assertEquals(movieId, movieDto.getId());
    }

    @Тогда("я получаю информацию по фильму")
    public void яПолучаюИнформациюПоФильму() {
        assertNotNull(movieDto);
    }

//    @Когда("я запрашиваю фильм с id {int}")
//    public void я_запрашиваю_фильм_с_id(Integer int1) {
//        // Write code here that turns the phrase above into concrete actions
//        System.out.println("я_запрашиваю_фильм_с_id " + int1);
//    }
//    @Тогда("я получаю информацию по фильму")
//    public void я_получаю_информацию_по_фильму() {
//        // Write code here that turns the phrase above into concrete actions
//        System.out.println("я_получаю_информацию_по_фильму");
//    }
}
