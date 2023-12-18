package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PayLawyersTest {
    private PayLawyers payLawyers;
    private Visitor visitorMock;
    private Character characterMock;

    @BeforeEach
    void initAction() {
        payLawyers = new PayLawyers();
    }

    @BeforeEach
    void initMocks() {
        visitorMock = mock(Visitor.class);
        characterMock = mock(Character.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        payLawyers.accept(visitorMock);

        verify(visitorMock).visit(payLawyers);
    }

    @Test
    void whenExecute_thenCallPayLawyers() {
        Characters characters = new Characters(List.of(characterMock));
        payLawyers.execute(characters);

        verify(characterMock).payLawyer(characters);
    }
}