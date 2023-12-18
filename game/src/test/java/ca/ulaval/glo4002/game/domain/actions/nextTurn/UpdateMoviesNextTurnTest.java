package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class UpdateMoviesNextTurnTest {
    private UpdateMoviesNextTurn updateMoviesNextTurn;
    private Characters charactersMock;
    private Movie movieMock;
    private List<Movie> movies;

    @BeforeEach
    void initUpdateMoviesNextTurn() {
        updateMoviesNextTurn = new UpdateMoviesNextTurn();
    }

    @BeforeEach
    void initCharacters() {
        charactersMock = mock(Characters.class);
    }

    @BeforeEach
    void initMoviesMock() {
        movieMock = mock(Movie.class);
        movies = List.of(movieMock);
    }

    @Test
    void whenCallAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        updateMoviesNextTurn.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(updateMoviesNextTurn);
    }

    @Test
    void whenExecute_thenMoviesAreUpdated() {
        updateMoviesNextTurn.execute(movies, charactersMock);

        verify(movieMock, times(movies.size())).updateNextTurn(charactersMock);
    }
}