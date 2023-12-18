package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class ApplyMovieBonus implements Action {

    private final int priority;

    public ApplyMovieBonus() {
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

    public void execute(List<Hamster> hamsters, List<Movie> movies) {
        hamsters.forEach(hamster -> hamster.applyMovieBonus(movies));
    }
}
