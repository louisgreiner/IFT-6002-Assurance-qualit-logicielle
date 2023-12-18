package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.HamstagramUser;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;
import java.util.Optional;

public class RealityShow implements Action {
    private static final float CHARACTER_PAYMENT = 50000;
    private static final int CHARACTER_REPUTATION_LOSS = 10;
    private final String from;
    private final int priority;

    public RealityShow(String from) {
        this.from = from;
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

    public void execute(Characters characters, List<Movie> movies) {
        if (characters.getByName(from).isEmpty()) {
            return;
        }

        Character character = characters.getByName(from).get();
        character.receivePayment(CHARACTER_PAYMENT);
        character.loseReputation(CHARACTER_REPUTATION_LOSS);

        Optional<HamstagramUser> optionalHamstagramUserFrom = characters.getByNameAndFilter(from, HamstagramUser.class);
        if (optionalHamstagramUserFrom.isEmpty()) {
            return;
        }

        HamstagramUser hamstagramUserFrom = optionalHamstagramUserFrom.get();
        hamstagramUserFrom.doRealityShow();
        movies.forEach(movie -> movie.removeHamster(from));
    }
}
