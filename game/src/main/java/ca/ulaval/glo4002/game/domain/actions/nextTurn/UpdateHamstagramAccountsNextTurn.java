package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;

public class UpdateHamstagramAccountsNextTurn implements Action {
    private final int priority;

    public UpdateHamstagramAccountsNextTurn() {
        this.priority = ActionEnumPriority.UPDATE_HAMSTAGRAM_ACCOUNTS_NEXT_TURN.getPriority();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void execute(Characters characters) {
        characters.getListOf(Hamster.class).forEach(Hamster::updateHamstagramAccountsNextTurn);
        characters.getListOf(Chinchilla.class).forEach(Chinchilla::updateHamstagramAccountsNextTurn);
    }
}
