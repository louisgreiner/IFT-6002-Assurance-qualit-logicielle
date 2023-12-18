package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;

public class CreateCharacter implements Action {
    private final Character character;
    private final int priority;

    public CreateCharacter(Character character) {
        this.character = character;
        this.priority = ActionEnumPriority.CREATE_CHARACTER.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void execute(Characters characters) {
        if (characters.getByName(character.getName()).isEmpty()) {
            characters.add(character);
        }
    }
}
