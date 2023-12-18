package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.movie.BoxOffice;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.movie.MovieLowBudget;
import ca.ulaval.glo4002.game.domain.movie.MovieStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateMovieTest {
    private static final String MOVIE_TITLE = "MovieTitle";

    private Movie movie;
    private CreateMovie createMovie;

    @BeforeEach
    void initMovie() {
        movie = new MovieLowBudget(new ArrayList<>(), new ArrayList<>(), MOVIE_TITLE, new BoxOffice(), MovieStatus.CREATING);
    }

    @BeforeEach
    void initCreateMovie() {
        createMovie = new CreateMovie(movie);
    }

    @Test
    void whenExecute_thenCreateMovie() {
        List<Movie> movies = new ArrayList<>();
        createMovie.execute(movies);
        assertTrue(movies.contains(movie));
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        createMovie.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(createMovie);
    }
}