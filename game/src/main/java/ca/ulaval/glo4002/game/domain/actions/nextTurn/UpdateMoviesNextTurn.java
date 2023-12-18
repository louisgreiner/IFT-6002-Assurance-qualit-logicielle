package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class UpdateMoviesNextTurn implements Action {
    private final int priority;

    public UpdateMoviesNextTurn() {
        this.priority = ActionEnumPriority.UPDATE_MOVIES_NEXT_TURN.getPriority();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void execute(List<Movie> movies, Characters characters) {
        movies.forEach(movie -> movie.updateNextTurn(characters));
    }
}
