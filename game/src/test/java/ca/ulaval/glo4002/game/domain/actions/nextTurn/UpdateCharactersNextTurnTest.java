package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class UpdateCharactersNextTurnTest {
    private UpdateCharactersNextTurn updateCharactersNextTurn;
    private Characters characters;
    private Hamster hamsterMock;

    @BeforeEach
    void initUpdateCharactersNextTurn() {
        updateCharactersNextTurn = new UpdateCharactersNextTurn();
    }

    @BeforeEach
    void initCharacters() {
        hamsterMock = mock(Hamster.class);
        characters = new Characters(List.of(hamsterMock));
    }

    @Test
    void whenCallAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        updateCharactersNextTurn.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(updateCharactersNextTurn);
    }

    @Test
    void whenExecute_thenCharactersAreUpdated() {
        updateCharactersNextTurn.execute(characters);

        verify(hamsterMock, times(characters.size())).updateNextTurn();
    }
}