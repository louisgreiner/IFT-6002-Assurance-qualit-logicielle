package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;

public class UpdateCharactersNextTurn implements Action {
    private final int priority;

    public UpdateCharactersNextTurn() {
        this.priority = ActionEnumPriority.UPDATE_CHARACTERS_NEXT_TURN.getPriority();
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
        characters.forEach(Character::updateNextTurn);
    }
}
