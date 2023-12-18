package ca.ulaval.glo4002.game.domain.rattedIn;

public class RattedInAccountFactory {

    public RattedInAccount create(String ownerName, RattedInStatus status) {
        return new RattedInAccount(ownerName, status);
    }
}
