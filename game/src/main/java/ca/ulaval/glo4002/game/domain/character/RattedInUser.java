package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;

public interface RattedInUser {
    void addRattedInContact(RattedInUser receiver);

    boolean acceptRattedInRequest(RattedInUser sender);

    void removeRattedInContact(RattedInUser contact);

    RattedInAccount getRattedInAccount();

    String getName();

    int getReputation();
}