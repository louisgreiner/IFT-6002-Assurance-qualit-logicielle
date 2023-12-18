package ca.ulaval.glo4002.game.domain.rattedIn;

import ca.ulaval.glo4002.game.domain.character.RattedInUser;

import java.util.ArrayList;
import java.util.List;

public class RattedInAccount {
    private final List<RattedInUser> contacts = new ArrayList<>();
    private final String ownerName;
    private RattedInStatus status;

    public RattedInAccount(String ownerName, RattedInStatus status) {
        this.ownerName = ownerName;
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public RattedInStatus getRattedInStatus() {
        return status;
    }

    public void setStatus(RattedInStatus status) {
        this.status = status;
    }

    public boolean isContactsEmpty() {
        return contacts.isEmpty();
    }

    public void addContact(RattedInUser rattedInUser) {
        if (!contacts.contains(rattedInUser)) {
            contacts.add(rattedInUser);
        }
    }

    public void removeContact(RattedInUser rattedInUser) {
        contacts.remove(rattedInUser);
    }

    public void clearContacts() {
        contacts.clear();
    }

    public boolean isInContacts(RattedInUser rattedInUser) {
        return contacts.contains(rattedInUser);
    }

    public List<RattedInUser> getContacts() {
        return contacts;
    }

    public void removeCharacterFromContacts(RattedInUser rattedInUser) {
        contacts.forEach(contact -> contact.removeRattedInContact(rattedInUser));
    }

}
