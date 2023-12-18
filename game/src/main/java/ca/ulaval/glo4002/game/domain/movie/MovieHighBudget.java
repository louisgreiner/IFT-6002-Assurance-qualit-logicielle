package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieHighBudget extends Movie {

    public MovieHighBudget(List<String> casting, List<String> potentialCasting, String title, BoxOffice boxOffice, MovieStatus status) {
        super(casting, potentialCasting, title, boxOffice, status);
    }

    public MovieHighBudget(String title) {
        super(title);
    }

    public MovieType getType() {
        return MovieType.A;
    }

    @Override
    public void chooseCasting(Characters characters) {
        if ((potentialCasting.size() + casting.size()) >= 2) {
            sortPotentialCastingFollowersBySalaryRatio(characters);

            int length = potentialCasting.size();

            String firstActorInPotentialCastingName = potentialCasting.get(length - 1);
            characters.getByNameAndFilter(firstActorInPotentialCastingName, Hamster.class).ifPresent(Hamster::makeNotAvailable);
            addToCasting(firstActorInPotentialCastingName);

            if (casting.size() == 1) {
                String secondActorInPotentialCastingName = potentialCasting.get(length - 2);
                characters.getByNameAndFilter(secondActorInPotentialCastingName, Hamster.class).ifPresent(Hamster::makeNotAvailable);
                addToCasting(secondActorInPotentialCastingName);
            }
        }
    }

    private void sortPotentialCastingFollowersBySalaryRatio(Characters characters) {
        ArrayList<Hamster> hamsters = new ArrayList<>(characters.getListOf(Hamster.class));
        List<String> potentialCastingSorted = new ArrayList<>();

        hamsters.sort(Comparator.comparing(Hamster::getFollowersSalaryRatio));

        for (Hamster hamster : hamsters) {
            String hamsterName = hamster.getName();
            if (potentialCasting.contains(hamsterName)) {
                potentialCastingSorted.add(hamsterName);
            }
        }
        potentialCasting = potentialCastingSorted;
    }
}
