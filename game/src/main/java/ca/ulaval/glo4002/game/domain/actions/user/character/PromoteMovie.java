package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.HamstagramUser;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PromoteMovie implements Action {
    private static final int MINIMAL_REPUTATION = 60;
    private static final int MINIMAL_FOLLOWERS = 9000;
    private final int priority;
    private final String fromName;

    public PromoteMovie(String fromName) {
        this.fromName = fromName;
        this.priority = ActionEnumPriority.CHARACTER_ACTION.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean canExecuteAction(Character character, String name) {
        return Objects.equals(character.getName(), name)
                && character.getReputation() >= MINIMAL_REPUTATION
                && hasEnoughFollowers((HamstagramUser) character);
    }

    public boolean hasEnoughFollowers(HamstagramUser hamstagramUser) {
        return hamstagramUser.getHamstagramFollowersNumber() >= MINIMAL_FOLLOWERS;
    }

    public void execute(Characters characters, List<Movie> movies) {
        Optional<Character> fromCharacterOptional = characters.stream()
                .filter(character -> canExecuteAction(character, fromName))
                .findFirst();
        if (fromCharacterOptional.isEmpty()) {
            return;
        }

        Optional<HamstagramUser> optionalHamstagramUserFrom = characters.getByNameAndFilter(fromName, HamstagramUser.class);
        if (optionalHamstagramUserFrom.isEmpty()) {
            return;
        }

        HamstagramUser hamstagramUserFrom = optionalHamstagramUserFrom.get();
        hamstagramUserFrom.promoteMovie(movies);
    }
}
