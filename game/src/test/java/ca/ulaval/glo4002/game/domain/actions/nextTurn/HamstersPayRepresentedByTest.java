package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class HamstersPayRepresentedByTest {
    private Hamster hamsterMock;
    private Characters charactersMock;
    private HamstersPayRepresentedBy hamstersPayRepresentedBy;

    @BeforeEach
    void initHamstersPayRepresentedBy() {
        hamstersPayRepresentedBy = new HamstersPayRepresentedBy();
    }

    @BeforeEach
    void initCharacterMock() {
        hamsterMock = mock(Hamster.class);
        charactersMock = mock(Characters.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        hamstersPayRepresentedBy.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(hamstersPayRepresentedBy);
    }

    @Test
    void whenExecute_thenHamstersPayRepresentedBy() {
        when(charactersMock.getListOf(Hamster.class)).thenReturn(List.of(hamsterMock));

        hamstersPayRepresentedBy.execute(charactersMock);

        verify(hamsterMock).payManager();
    }


}