package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;

public class HamstersPayRepresentedBy implements Action {
    private final int priority;

    public HamstersPayRepresentedBy() {
        this.priority = ActionEnumPriority.HAMSTERS_PAY_REPRESENTED_BY.getPriority();
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
        characters.getListOf(Hamster.class).forEach(Hamster::payManager);
    }
}
