package ca.ulaval.glo4002.game.domain.hamstagram;

public class HamstagramAccount {
    public static final int FOLLOWERS_LOSS_BY_TURN = 600;
    public static final int MINIMUM_FOLLOWERS = 1000;

    private final String ownerName;
    private int followersNumber;

    public HamstagramAccount(String ownerName, int followersNumber) {
        this.ownerName = ownerName;
        this.followersNumber = followersNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public void updateNextTurn() {
        followersNumber -= FOLLOWERS_LOSS_BY_TURN;
    }

    public boolean shouldBeEliminated() {
        return followersNumber < MINIMUM_FOLLOWERS;
    }

    public void addFollowers(int followers) {
        followersNumber += followers;
    }

    public void deductFollowersNumberFromRate(float followersLossRate) {
        followersNumber -= (int) Math.ceil(followersNumber * followersLossRate);
    }
}
