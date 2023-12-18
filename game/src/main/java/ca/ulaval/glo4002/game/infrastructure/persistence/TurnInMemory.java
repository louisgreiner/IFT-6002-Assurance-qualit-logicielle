package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.actions.Actions;
import ca.ulaval.glo4002.game.domain.turn.Turn;
import ca.ulaval.glo4002.game.domain.turn.TurnRepo;
import org.jvnet.hk2.annotations.Service;

@Service
public class TurnInMemory implements TurnRepo {
    private static Turn turn = new Turn(new Actions());

    @Override
    public Turn get() {
        return turn;
    }

    @Override
    public void update(Turn updatedTurn) {
        turn = updatedTurn;
    }
}
