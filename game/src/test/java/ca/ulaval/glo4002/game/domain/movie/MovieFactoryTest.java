package ca.ulaval.glo4002.game.domain.movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MovieFactoryTest {
    private static final String MOVIE_TITLE = "Movie title";

    private MovieFactory movieFactory;

    @BeforeEach
    void initMovieFactory() {
        movieFactory = new MovieFactory();
    }

    @Test
    void whenCreateMovieA_thenReturnMovieHighBudget() {
        MovieType movieType = MovieType.A;

        Movie movieCreated = movieFactory.create(MOVIE_TITLE, movieType);

        assertInstanceOf(MovieHighBudget.class, movieCreated);
    }

    @Test
    void whenCreateMovieB_thenReturnMovieLowBudget() {
        MovieType movieType = MovieType.B;

        Movie movieCreated = movieFactory.create(MOVIE_TITLE, movieType);

        assertInstanceOf(MovieLowBudget.class, movieCreated);
    }
}