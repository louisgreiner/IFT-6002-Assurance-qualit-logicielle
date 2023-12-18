package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;

import java.util.List;
import java.util.Optional;

public class Rat extends Character implements RattedInUser {
    private final RattedInAccount rattedInAccount;

    private Optional<String> client;

    public Rat(String name, Type type, int salary, int reputation, BankAccount bankAccount, RattedInAccount rattedInAccount) {
        super(name, type, salary, reputation, bankAccount);
        this.rattedInAccount = rattedInAccount;
        this.client = Optional.empty();
    }

    public boolean eliminateIfValidConditions() {
        if (reputation < MINIMAL_REPUTATION || bankAccount.shouldBeEliminated()) {
            rattedInAccount.removeCharacterFromContacts(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void receiveLawsuit(String actionCode, int turnNumber, Characters characters, List<Movie> movies) {
        super.receiveLawsuit(actionCode, turnNumber, characters, movies);
        if (client.isEmpty()) {
            return;
        }
        Optional<Character> clientCharacter = characters.getByName(this.client.get());
        if (clientCharacter.isEmpty()) {
            return;
        }
        clientCharacter.get().removeLawyer(characters);
    }

    public RattedInAccount getRattedInAccount() {
        return rattedInAccount;
    }


    public boolean acceptRattedInRequest(RattedInUser sender) {
        return true;
    }

    public void addRattedInContact(RattedInUser contact) {
        rattedInAccount.addContact(contact);
    }

    public void removeRattedInContact(RattedInUser contact) {
        rattedInAccount.removeContact(contact);
    }

    private void loseAllRattedInContacts() {
        rattedInAccount.clearContacts();
    }

    public boolean isAvailable() {
        return rattedInAccount.getRattedInStatus() == RattedInStatus.OPEN_TO_WORK
                && lawsuits.isEmpty();
    }

    public void acceptContract(String clientName) {
        client = Optional.of(clientName);
        rattedInAccount.setStatus(RattedInStatus.BUSY);
    }

    public void releaseContract() {
        client = Optional.empty();
        rattedInAccount.setStatus(RattedInStatus.OPEN_TO_WORK);
    }

    public void findLawyer(Characters characters) {
        Optional<Rat> possibleLawyer = findPossibleLawyer(characters, rattedInAccount);
        possibleLawyer.ifPresent(this::selectLawyer);
    }

    public void receiveHarassmentComplaint() {
        loseAllRattedInContacts();
    }

    protected Optional<String> getClient() {
        return client;
    }

    protected void setClient(Optional<String> client) {
        this.client = client;
    }
}
