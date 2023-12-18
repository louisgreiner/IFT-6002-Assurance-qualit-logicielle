package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class SpreadGossip implements Action {
    private final int priority;
    private final String fromName;
    private final String toName;

    public SpreadGossip(String fromName, String toName) {
        this.fromName = fromName;
        this.toName = toName;
        this.priority = ActionEnumPriority.CHARACTER_ACTION.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void execute(Characters characters, List<Movie> movies, int turnNumber) {
        if (characters.getByName(fromName).isEmpty() || characters.getByName(toName).isEmpty()) {
            return;
        }

        Character characterFrom = characters.getByName(fromName).get();
        Character characterTo = characters.getByName(toName).get();

        characterFrom.spreadGossip();
        characterTo.sufferFromGossip();
        characterFrom.receiveLawsuit("FR", turnNumber, characters, movies);
    }
}
