package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SpreadGossipTest {
    private static final String FROM_NAME = "fromName";
    private static final String TO_NAME = "toName";
    private static final int TURN_NUMBER = 23;

    private Character characterFromMock;
    private Character characterToMock;
    private SpreadGossip spreadGossip;

    @BeforeEach
    void initCharacterMock() {
        characterFromMock = mock(Character.class);
        characterToMock = mock(Character.class);
        when(characterFromMock.getName()).thenReturn(FROM_NAME);
        when(characterToMock.getName()).thenReturn(TO_NAME);
    }

    @BeforeEach
    void initSpreadGossip() {
        spreadGossip = new SpreadGossip(FROM_NAME, TO_NAME);
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        spreadGossip.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(spreadGossip);
    }

    @Test
    void whenExecute_thenCallSufferFromGossip() {
        Characters characters = new Characters(List.of(characterFromMock, characterToMock));

        spreadGossip.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(characterToMock).sufferFromGossip();
    }

    @Test
    void givenCharacterFromDoesNotExist_whenExecute_thenDoNothing() {
        Characters characters = new Characters(List.of(characterToMock));

        spreadGossip.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(characterToMock, never()).sufferFromGossip();
    }

    @Test
    void givenCharacterToDoesNotExist_whenExecute_thenDoNothing() {
        Characters characters = new Characters(List.of(characterFromMock));

        spreadGossip.execute(characters, new ArrayList<>(), TURN_NUMBER);

        verify(characterToMock, never()).sufferFromGossip();
    }
}