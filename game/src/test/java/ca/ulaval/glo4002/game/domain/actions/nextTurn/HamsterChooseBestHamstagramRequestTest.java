package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class HamsterChooseBestHamstagramRequestTest {
    private HamsterChooseBestHamstagramRequest hamsterChooseBestHamstagramRequest;
    private Characters charactersMock;
    private Hamster hamsterMock;

    @BeforeEach
    void initHamsterChooseBestHamstagramRequest() {
        hamsterChooseBestHamstagramRequest = new HamsterChooseBestHamstagramRequest();
    }

    @BeforeEach
    void initMock() {
        hamsterMock = mock(Hamster.class);
        charactersMock = mock(Characters.class);
    }


    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        hamsterChooseBestHamstagramRequest.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(hamsterChooseBestHamstagramRequest);
    }

    @Test
    void whenExecute_thenHamsterChooseBestHamstagramRequest() {
        when(charactersMock.getListOf(Hamster.class)).thenReturn(List.of(hamsterMock));
        List<Hamster> hamsters = List.of(hamsterMock);

        hamsterChooseBestHamstagramRequest.execute(charactersMock);

        hamsters.forEach(hamster -> verify(hamster).chooseBestHamstagramRequest());
    }
}