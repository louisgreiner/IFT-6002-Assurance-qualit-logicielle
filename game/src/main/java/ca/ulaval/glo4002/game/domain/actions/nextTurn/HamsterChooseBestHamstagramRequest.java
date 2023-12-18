package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;

public class HamsterChooseBestHamstagramRequest implements Action {
    private final int priority;

    public HamsterChooseBestHamstagramRequest() {
        this.priority = ActionEnumPriority.HAMSTER_CHOOSE_BEST_HAMSTAGRAM_REQUEST.getPriority();
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
        characters.getListOf(Hamster.class).forEach(Hamster::chooseBestHamstagramRequest);
    }
}
