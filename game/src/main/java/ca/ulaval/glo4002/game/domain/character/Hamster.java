package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hamster extends Character implements HamstagramCelebrity, HamstagramUser {
    private static final int REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN = 20000;

    private final HamstagramAccount hamstagramAccount;
    private final List<HamstagramManager> managersRequests;
    private boolean isFilming;
    private boolean canBeCast;
    private Optional<HamstagramManager> manager;
    private int unavailabilityTurnNumber;
    private boolean hasBonus;

    public Hamster(String name, Type type, int salary, int reputation, BankAccount bankAccount, HamstagramAccount hamstagramAccount) {
        super(name, type, salary, reputation, bankAccount);
        this.hamstagramAccount = hamstagramAccount;
        this.managersRequests = new ArrayList<>();
        this.isFilming = false;
        this.canBeCast = false;
        this.manager = Optional.empty();
        this.unavailabilityTurnNumber = 0;
        this.hasBonus = false;
    }

    public Hamster(String name, Type type, int salary, int reputation, BankAccount bankAccount, HamstagramAccount hamstagramAccount, HamstagramManager manager) {
        super(name, type, salary, reputation, bankAccount);
        this.hamstagramAccount = hamstagramAccount;
        this.managersRequests = new ArrayList<>();
        this.isFilming = false;
        this.canBeCast = false;
        this.manager = Optional.ofNullable(manager);
        this.unavailabilityTurnNumber = 0;
        this.hasBonus = false;
    }

    public int getUnavailabilityTurnNumber() {
        return unavailabilityTurnNumber;
    }

    public boolean getHasBonus() {
        return hasBonus;
    }

    public HamstagramAccount getHamstagramAccount() {
        return hamstagramAccount;
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
        giveBonus();
        applyMovieBonus(movies);
    }

    public void payManager() {
        manager.ifPresent(manager -> bankAccount.pay(manager, manager.getSalary()));
    }

    public void receiveSalary() {
        bankAccount.receiveSalary(salary);
    }

    @Override
    public void updateNextTurn() {
        super.updateNextTurn();
        canBeCast = !isFilming && (unavailabilityTurnNumber == 0) && lawsuits.isEmpty();
    }

    public boolean eliminateIfValidConditions() {
        if (reputation < MINIMAL_REPUTATION
                || bankAccount.shouldBeEliminated()
                || hamstagramAccount.shouldBeEliminated()) {
            if (!hasNoChinchilla()) {
                manager.ifPresent(manager -> manager.removeCelebrityRepresented(this));
            }
            return true;
        }
        return false;
    }

    public void handleHamstagramRequestFrom(Chinchilla sender) {
        if (hasNoChinchilla() && hasGreaterOrEqualReputationThan(sender)) {
            managersRequests.add(sender);
        }
    }

    public void chooseBestHamstagramRequest() {
        if (!managersRequests.isEmpty()) {
            Hamstrology hamstrology = new Hamstrology();
            manager = Optional.ofNullable(hamstrology.chooseChinchilla(managersRequests, this));
            managersRequests.clear();
        }
    }

    public boolean hasNoChinchilla() {
        return manager.isEmpty();
    }

    public void removeManager() {
        manager = Optional.empty();
    }

    @Override
    public Optional<HamstagramManager> getManager() {
        return manager;
    }

    public boolean canBeCast() {
        if (!lawsuits.isEmpty()) {
            canBeCast = false;
        }
        return canBeCast;
    }

    public float getFollowersSalaryRatio() {
        return (float) hamstagramAccount.getFollowersNumber() / getSalary();
    }

    public void startFilming() {
        isFilming = true;
        canBeCast = false;
    }

    public void endFilming() {
        isFilming = false;
        canBeCast = true;
    }

    public void makeNotAvailable() {
        canBeCast = !canBeCast;
    }

    public void decrementUnavailability() {
        unavailabilityTurnNumber = Math.max(0, unavailabilityTurnNumber - 1);
    }

    protected void sabotage(int unavailabilityTurnNumber) {
        this.unavailabilityTurnNumber = unavailabilityTurnNumber;
        canBeCast = false;
    }

    protected void giveBonus() {
        hasBonus = true;
    }

    public void applyMovieBonus(List<Movie> movies) {
        if (!hasBonus) {
            return;
        }
        for (Movie movie : movies) {
            if (movie.getCasting().contains(this.getName())) {
                movie.applyBonus();
                hasBonus = false;
                return;
            }
        }
    }

    public void applyBonusFromChinchilla(List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getCasting().contains(this.getName())) {
                movie.applyBonus();
            }
        }
    }

    private void loseFollowersFromRate(float followersLossRate) {
        hamstagramAccount.deductFollowersNumberFromRate(followersLossRate);
    }

    @Override
    public void receiveLawsuit(String actionCode, int turnNumber, Characters characters, List<Movie> movies) {
        super.receiveLawsuit(actionCode, turnNumber, characters, movies);
        movies.forEach(movie -> movie.removeHamster(this.getName()));
    }

    public void findLawyer(Characters characters) {
        if (getManager().isPresent()) {
            Optional<Rat> lawyerOptional = getManager().get().findLawyerForHamsters(characters);
            lawyerOptional.ifPresent(this::selectLawyer);
        }
    }

    public void receiveHarassmentComplaint() {
        loseFollowersFromRate(HARASSMENT_COMPLAINT_HAMSTAGRAM_FOLLOWERS_LOSS_RATE);
        sabotage(HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER);
    }

    protected boolean getIsFilming() {
        return isFilming;
    }
}
