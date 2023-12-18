package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;

import java.util.ArrayList;
import java.util.Comparator;

public class FindLawyer implements Action {

    private final int priority;

    public FindLawyer() {
        this.priority = ActionEnumPriority.FIND_LAWYER.getPriority();
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
        ArrayList<Character> characterArrayList = new ArrayList<>(characters);

        characterArrayList.sort(Comparator.comparingDouble(Character::getBankBalance));

        for (Character character : characterArrayList) {
            character.chooseLawyer(characters);
        }
    }
}
