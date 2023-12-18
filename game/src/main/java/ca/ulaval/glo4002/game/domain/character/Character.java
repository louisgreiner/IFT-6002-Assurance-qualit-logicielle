package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.bank.Payable;
import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;

import java.util.*;

public abstract class Character implements Payable {
    public static final int REPUTATION_LOSS_BY_TURN = 6;
    public static final int MINIMAL_REPUTATION = 15;
    public static final int REPUTATION_LOSS_BY_GOSSIP = 5;
    public static final int MAX_POSSIBLE_GOSSIPS = 3;
    protected final Salary salary;
    private final String name;
    private final Type type;
    protected int reputation;
    protected BankAccount bankAccount;
    protected boolean hasSpreadGossip;
    protected Optional<String> lawyer;
    protected List<Lawsuit> lawsuits;
    private int gossipsReceived;

    protected Character(String name, Type type, int salary, int reputation, BankAccount bankAccount) {
        this.name = name;
        this.type = type;
        this.salary = new Salary(salary);
        this.reputation = reputation;
        this.bankAccount = bankAccount;
        this.gossipsReceived = 0;
        this.hasSpreadGossip = false;
        this.lawyer = Optional.empty();
        this.lawsuits = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getSalary() {
        return salary.getSalary();
    }

    public int getReputation() {
        return reputation;
    }

    public float getBankBalance() {
        return bankAccount.getBalance();
    }

    public boolean getHasSpreadGossip() {
        return hasSpreadGossip;
    }

    public Optional<String> getLawyer() {
        return lawyer;
    }

    public void updateNextTurn() {
        bankAccount.updateNextTurn();
        loseReputation(REPUTATION_LOSS_BY_TURN);
    }

    public abstract boolean eliminateIfValidConditions();

    public void receivePayment(float amount) {
        bankAccount.receivePayment(amount);
    }

    protected boolean hasGreaterOrEqualReputationThan(Character character) {
        return reputation >= character.getReputation();
    }

    public Optional<HamstagramManager> getManager() {
        return Optional.empty();
    }

    public List<HamstagramCelebrity> getCelebrityRepresented() {
        return new ArrayList<>();
    }

    public void loseReputation(int reputationLoss) {
        reputation -= reputationLoss;
    }

    public void sufferFromGossip() {
        gossipsReceived++;
        int reputationLoss = REPUTATION_LOSS_BY_GOSSIP * Math.min(gossipsReceived, MAX_POSSIBLE_GOSSIPS);
        loseReputation(reputationLoss);
    }

    public void spreadGossip() {
        hasSpreadGossip = true;
    }

    public List<Lawsuit> getLawsuits() {
        return lawsuits;
    }

    public void receiveLawsuit(String actionCode, int turnNumber, Characters characters, List<Movie> movies) {
        lawsuits.add(new Lawsuit(turnNumber, name, actionCode, lawyer));
    }

    protected abstract void findLawyer(Characters characters);

    public void chooseLawyer(Characters characters) {
        if (lawsuits.isEmpty() || lawyer.isPresent()) {
            return;
        }
        findLawyer(characters);
    }

    public int getNbLawsuits() {
        return lawsuits.size();
    }

    protected Optional<Rat> findPossibleLawyer(Characters characters, RattedInAccount rattedInAccount) {
        ArrayList<Rat> possibleLawyers = new ArrayList<>();
        for (RattedInUser rattedInUser : rattedInAccount.getContacts()) {
            Optional<Rat> lawyer = characters.getByNameAndFilter(rattedInUser.getName(), Rat.class);
            if (lawyer.isEmpty()) {
                continue;
            }
            if (lawyer.get().isAvailable()) {
                possibleLawyers.add(lawyer.get());
            }
        }
        if (possibleLawyers.isEmpty()) {
            return Optional.empty();
        }

        possibleLawyers.sort(Comparator.comparingInt(Rat::getReputation));
        Collections.reverse(possibleLawyers);
        int bestReputation = possibleLawyers.get(0).getReputation();
        possibleLawyers.removeIf(rat -> rat.getReputation() < bestReputation);

        possibleLawyers.sort(Comparator.comparing(Rat::getName));

        return Optional.ofNullable(possibleLawyers.get(0));
    }

    public void selectLawyer(Rat lawyer) {
        lawyer.acceptContract(name);
        this.lawyer = Optional.ofNullable(lawyer.getName());
        lawsuits.forEach(lawsuit -> lawsuit.updateLawyer(this.lawyer));
    }

    public void payLawyer(Characters characters) {
        lawyer.ifPresent(lawyerName -> {
            Optional<Rat> rat = characters.getByNameAndFilter(lawyerName, Rat.class);
            rat.ifPresent(ratInstance -> bankAccount.pay(ratInstance, ratInstance.getSalary()));
        });
    }

    public void resolveLawsuits(Characters characters) {
        if (lawyer.isEmpty() || lawsuits.isEmpty()) {
            return;
        }
        lawsuits.remove(0);
        if (lawsuits.isEmpty()) {
            removeLawyer(characters);
        }
    }

    public abstract void receiveHarassmentComplaint();

    public void removeLawyer(Characters characters) {
        if (lawyer.isEmpty()) {
            return;
        }
        Optional<Rat> rat = characters.getByNameAndFilter(lawyer.get(), Rat.class);
        rat.ifPresent(Rat::releaseContract);
        lawyer = Optional.empty();
        lawsuits.forEach(lawsuit -> lawsuit.updateLawyer(lawyer));
    }
}
