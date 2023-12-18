package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class MoviesPayCharacters implements Action {
    private final int priority;

    public MoviesPayCharacters() {
        this.priority = ActionEnumPriority.MOVIES_PAY_CHARACTERS.getPriority();
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getPriority() {
        return priority;
    }

    public void execute(List<Movie> movies, Characters characters) {
        for (Movie movie : movies) {
            movie.payCharacters(characters);
        }
    }
}
