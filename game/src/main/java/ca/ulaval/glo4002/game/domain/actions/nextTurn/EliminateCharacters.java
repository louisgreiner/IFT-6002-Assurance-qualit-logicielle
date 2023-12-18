package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class EliminateCharacters implements Action {
    private final int priority;

    public EliminateCharacters() {
        this.priority = ActionEnumPriority.ELIMINATE_CHARACTERS.getPriority();
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getPriority() {
        return priority;
    }

    public void execute(Characters characters, List<Movie> movies) {
        characters.removeIf(Character::eliminateIfValidConditions);
        List<String> hamstersName = characters.getNameListOf(Hamster.class);
        for (Movie movie : movies) {
            movie.removeEliminatedHamsters(hamstersName);
        }
    }
}
