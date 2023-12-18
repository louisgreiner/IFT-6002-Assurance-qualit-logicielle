package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class EliminateCharactersTest {
    private EliminateCharacters eliminateCharacters;
    private Hamster hamsterMock;
    private Characters characters;
    private Movie movieMock;
    private List<Movie> movies;

    @BeforeEach
    void initEliminateCharacters() {
        eliminateCharacters = new EliminateCharacters();
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterMock = mock(Hamster.class);
        characters = new Characters(List.of(hamsterMock));
    }

    @BeforeEach
    void initMovieMock() {
        movieMock = mock(Movie.class);
        movies = List.of(movieMock);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        eliminateCharacters.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(eliminateCharacters);
    }

    @Test
    void whenExecute_thenCallEliminate() {
        eliminateCharacters.execute(characters, movies);

        verify(hamsterMock).eliminateIfValidConditions();
        verify(movieMock, times(movies.size())).removeEliminatedHamsters(characters.getNameListOf(Hamster.class));
    }

    @Test
    void givenEliminatedCharacter_whenExecute_thenCharacterIsRemoved() {
        when(hamsterMock.eliminateIfValidConditions()).thenReturn(true);

        eliminateCharacters.execute(characters, movies);

        assertFalse(characters.contains(hamsterMock));
    }
}