package ca.ulaval.glo4002.game.domain.movie;

import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.HamstagramManager;
import ca.ulaval.glo4002.game.domain.character.HamstagramUser;
import ca.ulaval.glo4002.game.domain.character.Hamster;

import java.util.ArrayList;
import java.util.List;

public class BoxOffice {
    private static final float HAMSTER_BOX_OFFICE_SHARE = 0.05F;
    private static final float CHINCHILLA_BOX_OFFICE_SHARE = 0.02F;
    private static final int INITIAL_BOX_OFFICE = 0;
    private static final int DEFAULT_BOX_OFFICE = 500000;
    private static final int BOX_OFFICE_DOLLARS_PER_FOLLOWERS = 10;
    private int amount;
    private int multiplier;

    public BoxOffice() {
        this.amount = INITIAL_BOX_OFFICE;
        this.multiplier = 1;
    }

    public int getAmount() {
        return amount;
    }

    protected int getMultiplier() {
        return multiplier;
    }

    public void calculateAmount(List<String> casting, Characters characters) {
        amount = DEFAULT_BOX_OFFICE;
        List<HamstagramManager> alreadyCalculatedManagers = new ArrayList<>();
        List<Hamster> hamsters = matchCastingFromCharacters(casting, characters);

        for (Hamster hamster : hamsters) {
            amount += hamster.getHamstagramFollowersNumber() * BOX_OFFICE_DOLLARS_PER_FOLLOWERS;
            if (hamster.getManager().isEmpty()) {
                continue;
            }

            HamstagramManager hamstagramManager = hamster.getManager().get();
            if (!alreadyCalculatedManagers.contains(hamstagramManager)) {
                alreadyCalculatedManagers.add(hamstagramManager);
                HamstagramUser user = (HamstagramUser) hamstagramManager;
                amount += user.getHamstagramFollowersNumber() * BOX_OFFICE_DOLLARS_PER_FOLLOWERS;
            }
        }
        amount *= multiplier;
    }

    private List<Hamster> matchCastingFromCharacters(List<String> casting, Characters characters) {
        List<Hamster> hamsters = new ArrayList<>();
        for (String cast : casting) {
            characters.getByNameAndFilter(cast, Hamster.class).ifPresent(hamsters::add);
        }
        return hamsters;
    }

    public void payCharacterBonus(Hamster hamster) {
        hamster.receivePayment(computeBonus(HAMSTER_BOX_OFFICE_SHARE, amount));
        hamster.getManager().ifPresent(chinchilla -> chinchilla.receivePayment(computeBonus(CHINCHILLA_BOX_OFFICE_SHARE, amount)));
    }

    protected float computeBonus(float boxOfficeShare, int amount) {
        return (float) Math.round(amount * boxOfficeShare * 100) / 100;
    }

    public void doubleRevenue() {
        this.multiplier *= 2;
    }
}
