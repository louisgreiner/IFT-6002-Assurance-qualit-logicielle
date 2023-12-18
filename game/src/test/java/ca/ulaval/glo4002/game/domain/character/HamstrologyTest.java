package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.bank.BankAccountFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HamstrologyTest {
    private static final int LOWEST_REPUTATION = 1;
    private static final int HIGHEST_REPUTATION = 2;
    private static final int SALARY = 1;
    private static final String NAME_1 = "Chinchilla1";
    private static final String NAME_2 = "Chinchilla2";
    private static final String HAMSTER_NAME = "A";
    private static final String CLOSEST_NAME_FROM_HAMSTER = "B";
    private static final String FURTHEST_NAME_FROM_HAMSTER = "Z";

    private Hamstrology hamstrology;
    private BankAccount bankAccount;
    private HamstagramAccount hamstagramAccount1;
    private HamstagramAccount hamstagramAccount2;
    private RattedInAccount rattedInAccount1;
    private RattedInAccount rattedInAccount2;

    @BeforeEach
    void initSocialAccounts() {
        HamstagramAccountFactory hamstagramAccountFactory = new HamstagramAccountFactory();
        hamstagramAccount1 = hamstagramAccountFactory.create(NAME_1);
        hamstagramAccount2 = hamstagramAccountFactory.create(NAME_2);

        RattedInAccountFactory rattedInAccountFactory = new RattedInAccountFactory();
        rattedInAccount1 = rattedInAccountFactory.create(NAME_1, RattedInStatus.NA);
        rattedInAccount2 = rattedInAccountFactory.create(NAME_2, RattedInStatus.NA);
    }

    @BeforeEach
    void initHamstrology() {
        this.hamstrology = new Hamstrology();
    }

    @BeforeEach
    void initBankAccount() {
        BankAccountFactory bankAccountFactory = new BankAccountFactory();
        bankAccount = bankAccountFactory.create();
    }

    @Test
    void whenComputeDistance_thenDistanceIsCorrect() {
        String chinchillaName = "A";
        String hamsterName = "B";
        int expectedDistance = 1;

        int distance = hamstrology.computeDistanceOfFirstChar(chinchillaName, hamsterName);

        assertEquals(expectedDistance, distance);
    }

    @Test
    void whenComputeDistanceInverted_thenDistanceIsCorrect() {
        String chinchillaName = "B";
        String hamsterName = "A";
        int expectedDistance = 1;

        int distance = hamstrology.computeDistanceOfFirstChar(chinchillaName, hamsterName);

        assertEquals(expectedDistance, distance);
    }

    @Test
    void givenNewChinchillaWithSameNameAndHigherReputation_whenShouldChooseNewChinchilla_thenReturnTrue() {
        Chinchilla chinchillaWithLowestReputation = new Chinchilla(NAME_1, Type.CHINCHILLA, SALARY, LOWEST_REPUTATION, bankAccount, hamstagramAccount1, rattedInAccount1);
        Chinchilla chinchillaWithHighestReputation = new Chinchilla(NAME_1, Type.CHINCHILLA, SALARY, HIGHEST_REPUTATION, bankAccount, hamstagramAccount2, rattedInAccount2);

        boolean shouldChooseNewChinchilla = hamstrology.shouldChooseNewChinchilla(chinchillaWithHighestReputation, chinchillaWithLowestReputation);

        assertTrue(shouldChooseNewChinchilla);
    }

    @Test
    void givenNewChinchillaWithSameNameAndLowerReputation_whenShouldChooseNewChinchilla_thenReturnFalse() {
        Chinchilla chinchillaWithLowestReputation = new Chinchilla(NAME_1, Type.CHINCHILLA, SALARY, LOWEST_REPUTATION, bankAccount, hamstagramAccount1, rattedInAccount1);
        Chinchilla chinchillaWithHighestReputation = new Chinchilla(NAME_1, Type.CHINCHILLA, SALARY, HIGHEST_REPUTATION, bankAccount, hamstagramAccount2, rattedInAccount2);

        boolean shouldChooseNewChinchilla = hamstrology.shouldChooseNewChinchilla(chinchillaWithLowestReputation, chinchillaWithHighestReputation);

        assertFalse(shouldChooseNewChinchilla);
    }

    @Test
    void givenChinchilla_whenChooseChinchilla_thenReturnChinchilla() {
        HamstagramManager expectedHamstagramManagerMock = mock(HamstagramManager.class);
        when(expectedHamstagramManagerMock.getName()).thenReturn(NAME_2);
        Hamster hamster = new Hamster(NAME_2, Type.HAMSTER, SALARY, LOWEST_REPUTATION, bankAccount, hamstagramAccount2);

        HamstagramManager hamstagramManagerSelected = hamstrology.chooseChinchilla(List.of(expectedHamstagramManagerMock), hamster);

        assertEquals(expectedHamstagramManagerMock, hamstagramManagerSelected);
    }

    @Test
    void givenTwoChinchillasWithDifferentNames_whenChooseChinchilla_thenReturnChinchillaWithClosestNameFromHamster() {
        HamstagramManager furthestHamstagramManagerMock = mock(HamstagramManager.class);
        HamstagramManager closestHamstagramManagerMock = mock(HamstagramManager.class);
        when(furthestHamstagramManagerMock.getName()).thenReturn(FURTHEST_NAME_FROM_HAMSTER);
        when(closestHamstagramManagerMock.getName()).thenReturn(CLOSEST_NAME_FROM_HAMSTER);

        Hamster hamster = new Hamster(HAMSTER_NAME, Type.HAMSTER, SALARY, LOWEST_REPUTATION, bankAccount, hamstagramAccount2);

        HamstagramManager hamstagramManagerSelected = hamstrology.chooseChinchilla(List.of(furthestHamstagramManagerMock, closestHamstagramManagerMock), hamster);

        assertEquals(closestHamstagramManagerMock, hamstagramManagerSelected);
    }

    @Test
    void givenNewChinchillaWithSameNameAndHigherReputation_whenChooseNewChinchilla_thenReturnChinchillaWithHighestReputation() {
        HamstagramManager hamstagramManagerWithHighestReputationMock = mock(HamstagramManager.class);
        when(hamstagramManagerWithHighestReputationMock.getName()).thenReturn(NAME_1);
        when(hamstagramManagerWithHighestReputationMock.getReputation()).thenReturn(HIGHEST_REPUTATION);
        HamstagramManager hamstagramManagerWithLowestReputationMock = mock(HamstagramManager.class);
        when(hamstagramManagerWithLowestReputationMock.getName()).thenReturn(NAME_2);
        when(hamstagramManagerWithLowestReputationMock.getReputation()).thenReturn(LOWEST_REPUTATION);
        Hamster hamster = new Hamster(HAMSTER_NAME, Type.HAMSTER, SALARY, LOWEST_REPUTATION, bankAccount, hamstagramAccount2);

        HamstagramManager hamstagramManagerSelected = hamstrology.chooseChinchilla(List.of(hamstagramManagerWithLowestReputationMock, hamstagramManagerWithHighestReputationMock), hamster);

        assertEquals(hamstagramManagerWithHighestReputationMock, hamstagramManagerSelected);
    }
}