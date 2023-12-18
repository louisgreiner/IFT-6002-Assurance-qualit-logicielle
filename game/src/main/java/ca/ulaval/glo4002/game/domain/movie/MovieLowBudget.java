package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Hamster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieLowBudget extends Movie {

    public MovieLowBudget(List<String> casting, List<String> potentialCasting, String title, BoxOffice boxOffice, MovieStatus status) {
        super(casting, potentialCasting, title, boxOffice, status);
    }

    public MovieLowBudget(String title) {
        super(title);
    }

    public MovieType getType() {
        return MovieType.B;
    }

    @Override
    public void chooseCasting(Characters characters) {
        if ((potentialCasting.size() + casting.size()) >= 2) {
            sortPotentialCastingBySalary(characters);

            String firstActorInPotentialCastingName = potentialCasting.get(0);
            characters.getByNameAndFilter(firstActorInPotentialCastingName, Hamster.class).ifPresent(Hamster::makeNotAvailable);
            addToCasting(firstActorInPotentialCastingName);

            if (casting.size() == 1) {
                String secondActorInPotentialCastingName = potentialCasting.get(1);
                characters.getByNameAndFilter(secondActorInPotentialCastingName, Hamster.class).ifPresent(Hamster::makeNotAvailable);
                addToCasting(secondActorInPotentialCastingName);
            }
        }
    }

    private void sortPotentialCastingBySalary(Characters characters) {
        ArrayList<Hamster> hamsters = new ArrayList<>(characters.getListOf(Hamster.class));
        List<String> potentialCastingSorted = new ArrayList<>();

        hamsters.sort(Comparator.comparingInt(Hamster::getSalary));

        for (Hamster hamster : hamsters) {
            String hamsterName = hamster.getName();
            if (potentialCasting.contains(hamsterName)) {
                potentialCastingSorted.add(hamsterName);
            }
        }
        potentialCasting = potentialCastingSorted;
    }
}