package ca.ulaval.glo4002.game.domain.hamstagram;

public class HamstagramAccountFactory {
    public final int DEFAULT_FOLLOWERS = 10000;

    public HamstagramAccount create(String ownerName) {
        return new HamstagramAccount(ownerName, DEFAULT_FOLLOWERS);
    }
}
