package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Chinchilla extends Character implements RattedInUser, HamstagramUser, HamstagramManager {
    private static final int REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN = 8000;

    private final HamstagramAccount hamstagramAccount;
    private final RattedInAccount rattedInAccount;
    private final List<HamstagramCelebrity> celebrityRepresented;
    private int unavailabilityTurnNumber;

    public Chinchilla(String name, Type type, int salary, int reputation, BankAccount bankAccount, HamstagramAccount hamstagramAccount, RattedInAccount rattedInAccount) {
        super(name, type, salary, reputation, bankAccount);
        this.hamstagramAccount = hamstagramAccount;
        this.rattedInAccount = rattedInAccount;
        this.celebrityRepresented = new ArrayList<>();
        this.unavailabilityTurnNumber = 0;
    }

    public HamstagramAccount getHamstagramAccount() {
        return hamstagramAccount;
    }

    public RattedInAccount getRattedInAccount() {
        return rattedInAccount;
    }

    public int getUnavailabilityTurnNumber() {
        return unavailabilityTurnNumber;
    }

    public void updateHamstagramAccountsNextTurn() {
        hamstagramAccount.updateNextTurn();
    }

    public int getHamstagramFollowersNumber() {
        return hamstagramAccount.getFollowersNumber();
    }

    public void doRealityShow() {
        hamstagramAccount.addFollowers(REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN);
        sabotage(REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER);
    }

    public void receiveScandal() {
        loseFollowersFromRate(SCANDAL_HAMSTAGRAM_FOLLOWERS_LOSS_RATE);
        sabotage(SCANDAL_UNAVAILABILITY_TURN_NUMBER);
    }

    public void promoteMovie(List<Movie> movies) {
        List<HamstagramCelebrity> hamsters = getCelebrityRepresented();
        hamsters.forEach(hamster -> hamster.applyBonusFromChinchilla(movies));
    }

    public boolean eliminateIfValidConditions() {
        if (reputation < MINIMAL_REPUTATION
                || bankAccount.shouldBeEliminated()
                || hamstagramAccount.shouldBeEliminated()) {
            celebrityRepresented.forEach(HamstagramCelebrity::removeManager);
            rattedInAccount.removeCharacterFromContacts(this);
            return true;
        }
        return false;
    }

    public void sendHamstagramRequests(List<Hamster> hamsters) {
        if (unavailabilityTurnNumber == 0 && lawsuits.isEmpty()) {
            for (Hamster hamster : hamsters) {
                hamster.handleHamstagramRequestFrom(this);
            }
        }
    }

    public void removeCelebrityRepresented(Hamster hamster) {
        celebrityRepresented.remove(hamster);
    }

    public void representNewHamster(Hamster hamster) {
        celebrityRepresented.add(hamster);
    }

    public List<HamstagramCelebrity> getCelebrityRepresented() {
        return celebrityRepresented;
    }

    public boolean isRepresentEmpty() {
        return celebrityRepresented.isEmpty();
    }

    public boolean acceptRattedInRequest(RattedInUser sender) {
        return sender.getReputation() >= this.reputation;
    }

    public void addRattedInContact(RattedInUser receiver) {
        rattedInAccount.addContact(receiver);
    }

    public void removeRattedInContact(RattedInUser character) {
        rattedInAccount.removeContact(character);
    }

    public void decrementUnavailability() {
        unavailabilityTurnNumber = Math.max(0, unavailabilityTurnNumber - 1);
    }

    protected void sabotage(int unavailabilityTurnNumber) {
        this.unavailabilityTurnNumber = unavailabilityTurnNumber + 1;
        loseAllActors();
    }

    private void loseAllActors() {
        for (HamstagramCelebrity hamstagramCelebrity : celebrityRepresented) {
            hamstagramCelebrity.removeManager();
        }
        celebrityRepresented.clear();
    }

    private void loseFollowersFromRate(float followersLossRate) {
        hamstagramAccount.deductFollowersNumberFromRate(followersLossRate);
    }

    private void loseAllRattedInContacts() {
        rattedInAccount.clearContacts();
    }


    public Optional<Rat> findLawyerForHamsters(Characters characters) {
        return findPossibleLawyer(characters, rattedInAccount);
    }

    @Override
    public void receiveLawsuit(String actionCode, int turnNumber, Characters characters, List<Movie> movies) {
        super.receiveLawsuit(actionCode, turnNumber, characters, movies);
        loseAllActors();
    }

    public void findLawyer(Characters characters) {
        Optional<Rat> possibleLawyer = findPossibleLawyer(characters, rattedInAccount);
        possibleLawyer.ifPresent(this::selectLawyer);
    }

    public void receiveHarassmentComplaint() {
        loseFollowersFromRate(HARASSMENT_COMPLAINT_HAMSTAGRAM_FOLLOWERS_LOSS_RATE);
        loseAllRattedInContacts();
        sabotage(HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER);
    }
}