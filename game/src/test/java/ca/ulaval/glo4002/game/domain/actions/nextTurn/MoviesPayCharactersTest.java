package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Type;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class MoviesPayCharactersTest {
    private static final String FROM_NAME = "fromName";

    private MoviesPayCharacters moviesPayCharacters;
    private Movie movieMock;
    private Hamster hamsterMock;


    @BeforeEach
    void initMoviesPayCharacters() {
        moviesPayCharacters = new MoviesPayCharacters();
    }

    @BeforeEach
    void initMovieMock() {
        movieMock = mock(Movie.class);
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterMock = mock(Hamster.class);
        when(hamsterMock.getName()).thenReturn(FROM_NAME);
        when(hamsterMock.getType()).thenReturn(Type.HAMSTER);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        moviesPayCharacters.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(moviesPayCharacters);
    }

    @Test
    void whenExecute_thenCharactersArePaid() {
        Characters characters = new Characters(List.of(hamsterMock));

        moviesPayCharacters.execute(List.of(movieMock), characters);

        verify(movieMock).payCharacters(characters);
    }
}