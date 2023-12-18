package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;

public class ResolveLawsuits implements Action {

    private final int priority;

    public ResolveLawsuits() {
        this.priority = ActionEnumPriority.RESOLVE_LAWSUITS.getPriority();
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getPriority() {
        return priority;
    }

    public void execute(Characters characters) {
        characters.forEach(character -> character.resolveLawsuits(characters));
    }
}
