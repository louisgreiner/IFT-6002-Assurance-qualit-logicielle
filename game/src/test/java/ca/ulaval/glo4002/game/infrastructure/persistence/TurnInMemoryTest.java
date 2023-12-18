package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.actions.Actions;
import ca.ulaval.glo4002.game.domain.turn.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TurnInMemoryTest {
    private TurnInMemory turnInMemory;
    private Actions actionsMock;

    @BeforeEach
    void initTurnInMemory() {
        turnInMemory = new TurnInMemory();
    }

    @BeforeEach
    void initActionsMock() {
        actionsMock = mock(Actions.class);
    }

    @Test
    void whenUpdatedTurn_thenGetTurnReturnsUpdatedTurn() {
        Turn turn = new Turn(actionsMock);

        turnInMemory.update(turn);

        Turn updatedTurn = turnInMemory.get();
        assertEquals(turn, updatedTurn);
    }
}