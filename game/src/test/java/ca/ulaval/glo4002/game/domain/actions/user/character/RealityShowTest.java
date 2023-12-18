package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Rat;
import ca.ulaval.glo4002.game.domain.character.Type;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class RealityShowTest {
    private static final String FROM_NAME = "fromName";

    private RealityShow realityShow;
    private Hamster hamsterMock;
    private Rat ratMock;
    private Movie movieMock;
    private List<Movie> movies;

    @BeforeEach
    void initRealityShow() {
        realityShow = new RealityShow(FROM_NAME);
    }

    @BeforeEach
    void initHamsterMock() {
        hamsterMock = mock(Hamster.class);
        when(hamsterMock.getName()).thenReturn(FROM_NAME);
        when(hamsterMock.getType()).thenReturn(Type.HAMSTER);
    }

    @BeforeEach
    void initRatMock() {
        ratMock = mock(Rat.class);
        when(ratMock.getName()).thenReturn(FROM_NAME);
        when(ratMock.getType()).thenReturn(Type.RAT);
    }

    @BeforeEach
    void initMovieMock() {
        movieMock = mock(Movie.class);
        movies = List.of(movieMock);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        realityShow.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(realityShow);
    }

    @Test
    void givenCharacter_whenExecute_thenCallReceivePaymentAndLoseReputation() {
        Characters characters = new Characters(List.of(hamsterMock));

        realityShow.execute(characters, movies);

        verify(hamsterMock).receivePayment(anyFloat());
        verify(hamsterMock).loseReputation(anyInt());
    }

    @Test
    void whenExecuteWithHamstagramUser_thenCallConsequences() {
        Characters characters = new Characters(List.of(hamsterMock));

        realityShow.execute(characters, movies);

        verify(hamsterMock).doRealityShow();
        verify(movieMock, times(movies.size())).removeHamster(hamsterMock.getName());
    }

    @Test
    void whenExecuteWithNotHamstagramUser_thenCallConsequences() {
        Characters characters = new Characters(List.of(ratMock));

        realityShow.execute(characters, movies);

        verify(hamsterMock, never()).doRealityShow();
        verify(movieMock, never()).removeHamster(hamsterMock.getName());
    }
}