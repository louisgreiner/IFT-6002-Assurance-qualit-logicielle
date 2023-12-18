package ca.ulaval.glo4002.game.domain.hamstagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HamstagramAccountTest {
    private static final String OWNER_NAME = "TestOwnerName";
    private static final int DEFAULT_FOLLOWERS = 10000;

    private HamstagramAccount hamstagramAccount;

    @BeforeEach
    void initHamstagramAccount() {
        hamstagramAccount = new HamstagramAccount(OWNER_NAME, DEFAULT_FOLLOWERS);
    }

    @Test
    void whenDeductFollowers_thenDeductFollowers() {
        int followersLossByTurn = 600;
        int expectedFollowers = hamstagramAccount.getFollowersNumber() - followersLossByTurn;

        hamstagramAccount.updateNextTurn();

        assertEquals(expectedFollowers, hamstagramAccount.getFollowersNumber());
    }

    @Test
    void givenFollowersNumberBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        int invalidFollowersNumber = 999;
        HamstagramAccount invalidHamstagramAccount = new HamstagramAccount(OWNER_NAME, invalidFollowersNumber);

        boolean isHamstagramAccountShouldBeEliminated = invalidHamstagramAccount.shouldBeEliminated();

        assertTrue(isHamstagramAccountShouldBeEliminated);
    }

    @Test
    void whenAddFollowers_thenFollowersNumberIsUpdated() {
        int followersToAdd = 500;
        int expectedFollowersNumber = 10500;

        hamstagramAccount.addFollowers(followersToAdd);

        assertEquals(expectedFollowersNumber, hamstagramAccount.getFollowersNumber());
    }

    @Test
    void whenDeductFollowersNumberFromRate_thenFollowersNumberIsUpdated() {
        float followersLossRate = 0.4F;
        int expectedFollowersNumber = 6000;

        hamstagramAccount.deductFollowersNumberFromRate(followersLossRate);

        assertEquals(expectedFollowersNumber, hamstagramAccount.getFollowersNumber());
    }
}