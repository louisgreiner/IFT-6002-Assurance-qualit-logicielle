package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;

public class UpdateUnavailability implements Action {
    private final int priority;

    public UpdateUnavailability() {
        this.priority = ActionEnumPriority.CHARACTER_ACTION.getPriority();
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
        characters.getListOf(Hamster.class).forEach(Hamster::decrementUnavailability);
        characters.getListOf(Chinchilla.class).forEach(Chinchilla::decrementUnavailability);
    }
}
