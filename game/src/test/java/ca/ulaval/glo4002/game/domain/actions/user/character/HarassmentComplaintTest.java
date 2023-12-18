package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class HarassmentComplaintTest {
    private static final String FROM_NAME = "fromName";
    private static final String TO_NAME = "toName";
    private static final int TURN_NUMBER = 23;

    private Hamster hamsterFromMock;
    private Hamster hamsterToMock;
    private Movie movieMock;
    private List<Movie> movies;
    private HarassmentComplaint harassmentComplaint;

    @BeforeEach
    void initHarassmentComplaint() {
        harassmentComplaint = new HarassmentComplaint(FROM_NAME, TO_NAME);
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterFromMock = mock(Hamster.class);
        when(hamsterFromMock.getName()).thenReturn(FROM_NAME);
        when(hamsterFromMock.getHasSpreadGossip()).thenReturn(false);
        hamsterToMock = mock(Hamster.class);
        when(hamsterToMock.getName()).thenReturn(TO_NAME);
    }

    @BeforeEach
    void initMovieMock() {
        movieMock = mock(Movie.class);
        movies = List.of(movieMock);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        harassmentComplaint.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(harassmentComplaint);
    }

    @Test
    void givenCharacterHasNotSpreadGossip_whenExecute_thenCallConsequences() {
        Characters characters = new Characters(List.of(hamsterFromMock, hamsterToMock));

        harassmentComplaint.execute(characters, movies, TURN_NUMBER);

        verify(hamsterFromMock).receiveLawsuit(anyString(), anyInt(), any(), any());
        verify(hamsterToMock).loseReputation(anyInt());
        verify(movieMock).removeHamster(anyString());
        verify(hamsterToMock).receiveHarassmentComplaint();
    }

    @Test
    void givenCharacterHasSpreadGossip_whenExecute_thenCallConsequences() {
        Characters characters = new Characters(List.of(hamsterFromMock, hamsterToMock));
        when(hamsterFromMock.getHasSpreadGossip()).thenReturn(true);

        harassmentComplaint.execute(characters, movies, TURN_NUMBER);

        verify(hamsterFromMock).receiveLawsuit(anyString(), anyInt(), any(), any());
        verify(movieMock, never()).removeHamster(anyString());
        verify(hamsterToMock, never()).loseReputation(anyInt());
        verify(hamsterToMock, never()).receiveHarassmentComplaint();
    }
}