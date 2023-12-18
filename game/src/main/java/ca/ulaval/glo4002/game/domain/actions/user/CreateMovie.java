package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class CreateMovie implements Action {

    private final Movie movie;
    private final int priority;

    public CreateMovie(Movie movie) {
        this.movie = movie;
        this.priority = ActionEnumPriority.CREATE_MOVIE.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void execute(List<Movie> movies) {
        movies.add(movie);
    }
}
