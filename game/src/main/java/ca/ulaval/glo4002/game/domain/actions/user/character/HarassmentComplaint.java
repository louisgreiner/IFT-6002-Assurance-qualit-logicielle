package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;

public class HarassmentComplaint implements Action {
    private static final int CHARACTER_REPUTATION_LOSS = 10;

    private final String from;
    private final String to;
    private final int priority;

    public HarassmentComplaint(String from, String to) {
        this.from = from;
        this.to = to;
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

    public void execute(Characters characters, List<Movie> movies, int turnNumber) {
        if (characters.getByName(from).isEmpty() || characters.getByName(to).isEmpty()) {
            return;
        }

        Character characterFrom = characters.getByName(from).get();
        Character characterTo = characters.getByName(to).get();

        characterFrom.receiveLawsuit("PL", turnNumber, characters, movies);

        if (characterFrom.getHasSpreadGossip()) {
            return;
        }

        characterTo.loseReputation(CHARACTER_REPUTATION_LOSS);
        movies.forEach(movie -> movie.removeHamster(characterTo.getName()));
        characterTo.receiveHarassmentComplaint();
    }
}
