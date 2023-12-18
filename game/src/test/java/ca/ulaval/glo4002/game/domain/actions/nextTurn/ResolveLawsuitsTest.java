package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ResolveLawsuitsTest {
    private ResolveLawsuits resolveLawsuits;
    private Visitor visitorMock;
    private Character characterMock;

    @BeforeEach
    void initResolveLawsuits() {
        resolveLawsuits = new ResolveLawsuits();
    }

    @BeforeEach
    void initMocks() {
        visitorMock = mock(Visitor.class);
        characterMock = mock(Character.class);
    }

    @Test
    void whenAccept_thenCallVisit() {
        resolveLawsuits.accept(visitorMock);

        verify(visitorMock).visit(resolveLawsuits);
    }

    @Test
    void whenExecute_thenCallResolveLawsuits() {
        Characters characters = new Characters(List.of(characterMock));
        resolveLawsuits.execute(characters);

        verify(characterMock).resolveLawsuits(characters);
    }
}