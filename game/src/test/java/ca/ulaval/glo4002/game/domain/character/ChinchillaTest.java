package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ChinchillaTest {
    private static final float SCANDAL_FOLLOWERS_LOSS_RATE = 0.4F;
    private static final int SCANDAL_UNAVAILABILITY_TURN_NUMBER = 2;
    private static final int REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN = 8000;
    private static final int REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER = 2;
    private static final float HARASSMENT_COMPLAINT_FOLLOWERS_LOSS_RATE = 0.7F;
    private static final int HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER = 3;
    private static final int UNAVAILABILITY_TURN_NUMBER = 4;
    private static final String NAME = "TestName";
    private static final String CONTACT_NAME = "TestContactName";
    private static final int SALARY = 250;
    private static final Type CHARACTER_TYPE_CHINCHILLA = Type.CHINCHILLA;
    private static final Type CHARACTER_TYPE_HAMSTER = Type.HAMSTER;
    private static final int VALID_REPUTATION = 15;
    private static final String REPRESENTED_HAMSTER_NAME = "representedHamsterName";

    private BankAccount validBankAccount;
    private BankAccount invalidBankAccount;
    private RattedInAccount rattedInAccount;
    private HamstagramAccount hamstagramAccount;
    private Chinchilla chinchilla;
    private CharacterFactory characterFactory;
    private BankAccount bankAccountMock;
    private HamstagramAccount hamstagramAccountMock;
    private RattedInAccount rattedInAccountMock;
    private Hamster hamsterMock;

    @BeforeEach
    void initSocialAccountsMock() {
        hamstagramAccountMock = mock(HamstagramAccount.class);
        rattedInAccountMock = mock(RattedInAccount.class);
    }

    @BeforeEach
    void initBankAccountMock() {
        bankAccountMock = mock(BankAccount.class);
    }

    @BeforeEach
    void initHamstagramCelebrityMock() {
        hamsterMock = mock(Hamster.class);
    }

    @BeforeEach
    void initChinchilla() {
        validBankAccount = new BankAccount(1000);
        invalidBankAccount = new BankAccount(-1);

        HamstagramAccountFactory hamstagramAccountFactory = new HamstagramAccountFactory();
        hamstagramAccount = hamstagramAccountFactory.create(NAME);

        RattedInAccountFactory rattedInAccountFactory = new RattedInAccountFactory();
        rattedInAccount = rattedInAccountFactory.create(NAME, RattedInStatus.OPEN_TO_WORK);

        characterFactory = new CharacterFactory();
        chinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccount);
    }

    @Test
    void whenUpdateNextTurn_thenUpdateReputation() {
        int reputationLossByTurn = 6;
        int expectedReputation = chinchilla.getReputation() - reputationLossByTurn;

        chinchilla.updateNextTurn();

        assertEquals(expectedReputation, chinchilla.getReputation());
    }

    @Test
    void whenUpdateNextTurn_thenUpdateBankBalance() {
        int bankBalanceLossByTurn = 100;
        float expectedBankBalance = chinchilla.getBankBalance() - bankBalanceLossByTurn;

        chinchilla.updateNextTurn();

        assertEquals(expectedBankBalance, chinchilla.getBankBalance());
    }

    @Test
    void whenUpdateHamstagramAccountsNextTurn_thenCallHamstagramAccountUpdateNextTurn() {
        Chinchilla chinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock, rattedInAccount);

        chinchilla.updateHamstagramAccountsNextTurn();

        verify(hamstagramAccountMock).updateNextTurn();
    }

    @Test
    void givenReputationAndBankAccountAboveMinimal_whenShouldBeEliminated_thenReturnFalse() {
        Chinchilla validChinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccount);

        boolean isChinchillaShouldBeEliminated = validChinchilla.eliminateIfValidConditions();

        assertFalse(isChinchillaShouldBeEliminated);
    }

    @Test
    void givenReputationBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        int invalidReputation = 14;
        Chinchilla invalidChinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, invalidReputation, validBankAccount, hamstagramAccount, rattedInAccount);

        boolean isChinchillaShouldBeEliminated = invalidChinchilla.eliminateIfValidConditions();

        assertTrue(isChinchillaShouldBeEliminated);
    }

    @Test
    void givenBankBalanceBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        Chinchilla invalidChinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, invalidBankAccount, hamstagramAccount, rattedInAccount);

        boolean isChinchillaShouldBeEliminated = invalidChinchilla.eliminateIfValidConditions();

        assertTrue(isChinchillaShouldBeEliminated);
    }

    @Test
    void whenReceivePayment_thenCallReceivePayment() {
        Chinchilla chinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount, rattedInAccount);
        float amount = 100;

        chinchilla.receivePayment(amount);

        verify(bankAccountMock).receivePayment(amount);
    }

    @Test
    void whenContactRattedInRequestIsReceived_thenAccept() {
        RattedInUser receiver = chinchilla;
        RattedInUser sender = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccount);

        boolean isAccepted = receiver.acceptRattedInRequest(sender);

        assertTrue(isAccepted);
    }

    @Test
    void givenNotAcceptableContactSender_whenContactRequestIsReceived_thenDecline() {
        RattedInUser receiver = chinchilla;
        int senderReputation = receiver.getReputation() - 1;
        RattedInUser notValidSender = new Chinchilla(CONTACT_NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, senderReputation, validBankAccount, hamstagramAccount, rattedInAccount);

        boolean isAccepted = receiver.acceptRattedInRequest(notValidSender);

        assertFalse(isAccepted);
    }

    @Test
    void whenSendHamstagramRequests_thenCallHandleHamstagramRequestFrom() {
        Hamster hamster = mock(Hamster.class);
        List<Hamster> hamsters = List.of(hamster);

        chinchilla.sendHamstagramRequests(hamsters);

        verify(hamster).handleHamstagramRequestFrom(chinchilla);
    }

    @Test
    void givenNoCharacterInRepresent_whenIsRepresentEmpty_thenReturnsTrue() {
        assertTrue(chinchilla.isRepresentEmpty());
    }

    @Test
    void givenCharacterInRepresent_whenIsRepresentEmpty_thenReturnsFalse() {
        Hamster hamster = (Hamster) characterFactory.create(CONTACT_NAME, CHARACTER_TYPE_HAMSTER, SALARY);
        chinchilla.representNewHamster(hamster);

        assertFalse(chinchilla.isRepresentEmpty());
    }

    @Test
    void givenAHamsterInRepresent_whenRemoveHamsterInRepresent_thenHamsterRemovedFromRepresent() {
        Hamster representedHamster = (Hamster) characterFactory.create(REPRESENTED_HAMSTER_NAME, CHARACTER_TYPE_HAMSTER, SALARY);
        chinchilla.representNewHamster(representedHamster);

        chinchilla.removeCelebrityRepresented(representedHamster);

        boolean representIsEmpty = chinchilla.isRepresentEmpty();
        assertTrue(representIsEmpty);
    }

    @Test
    void whenAddRattedInContact_thenCallAddContact() {
        RattedInUser chinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccountMock);
        RattedInUser receiver = new Rat(CONTACT_NAME, Type.RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountMock);

        chinchilla.addRattedInContact(receiver);

        verify(rattedInAccountMock).addContact(receiver);
    }

    @Test
    void whenRemoveRattedInContact_thenCallRemoveContact() {
        RattedInUser chinchilla = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccountMock);
        RattedInUser receiver = new Rat(CONTACT_NAME, Type.RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountMock);

        chinchilla.removeRattedInContact(receiver);

        verify(rattedInAccountMock).removeContact(receiver);
    }

    @Test
    void givenUnavailability_whenDecrementUnavailability_thenUnavailabilityIsDecremented() {
        chinchilla.sabotage(UNAVAILABILITY_TURN_NUMBER);

        chinchilla.decrementUnavailability();

        int expectedUnavailability = UNAVAILABILITY_TURN_NUMBER + 1 - 1;
        assertEquals(expectedUnavailability, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void givenUnavailabilityIsZero_whenDecrementUnavailability_thenUnavailabilityIsNotDecremented() {
        chinchilla.decrementUnavailability();

        int expectedUnavailability = 0;
        assertEquals(expectedUnavailability, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void whenSabotage_thenUpdateUnavailabilityTurnNumber() {
        chinchilla.sabotage(UNAVAILABILITY_TURN_NUMBER);

        int expectedUnavailability = UNAVAILABILITY_TURN_NUMBER + 1;
        assertEquals(expectedUnavailability, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void whenSabotage_thenRemoveRepresentByChinchillaIsCalled() {
        Hamster hamsterMock = mock(Hamster.class);
        chinchilla.representNewHamster(hamsterMock);
        chinchilla.sabotage(UNAVAILABILITY_TURN_NUMBER);

        verify(hamsterMock).removeManager();
    }

    @Test
    void whenSabotage_thenRepresentIsEmpty() {
        Hamster hamsterMock = mock(Hamster.class);
        chinchilla.representNewHamster(hamsterMock);

        chinchilla.sabotage(UNAVAILABILITY_TURN_NUMBER);

        assertTrue(chinchilla.getCelebrityRepresented().isEmpty());
    }

    @Test
    void whenDoRealityShow_thenAddChinchillaFollowers() {
        Chinchilla chinchillaWithMockedHamstagramAccount = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock, rattedInAccount);
        chinchillaWithMockedHamstagramAccount.doRealityShow();

        verify(hamstagramAccountMock).addFollowers(REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN);
    }

    @Test
    void whenDoRealityShow_thenSabotage() {
        Hamster hamsterMock = mock(Hamster.class);
        chinchilla.representNewHamster(hamsterMock);

        chinchilla.doRealityShow();

        assertTrue(chinchilla.getCelebrityRepresented().isEmpty());
        assertEquals(REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER + 1, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void whenReceiveScandal_thenLoseFollowersFromRate() {
        Chinchilla chinchillaWithMockedHamstagramAccount = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock, rattedInAccount);

        chinchillaWithMockedHamstagramAccount.receiveScandal();

        verify(hamstagramAccountMock).deductFollowersNumberFromRate(SCANDAL_FOLLOWERS_LOSS_RATE);
    }

    @Test
    void whenReceiveScandal_thenSabotage() {
        Hamster hamsterMock = mock(Hamster.class);
        chinchilla.representNewHamster(hamsterMock);

        chinchilla.receiveScandal();

        assertTrue(chinchilla.getCelebrityRepresented().isEmpty());
        assertEquals(SCANDAL_UNAVAILABILITY_TURN_NUMBER + 1, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void whenReceiveHarassmentComplaint_thenLoseFollowersFromRate() {
        Chinchilla chinchillaWithMockedHamstagramAccount = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock, rattedInAccount);

        chinchillaWithMockedHamstagramAccount.receiveHarassmentComplaint();

        verify(hamstagramAccountMock).deductFollowersNumberFromRate(HARASSMENT_COMPLAINT_FOLLOWERS_LOSS_RATE);
    }

    @Test
    void whenReceiveHarassmentComplaint_thenLoseAllRattedInContacts() {
        Chinchilla chinchillaWithMockedRattedInAccount = new Chinchilla(NAME, CHARACTER_TYPE_CHINCHILLA, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount, rattedInAccountMock);

        chinchillaWithMockedRattedInAccount.receiveHarassmentComplaint();

        verify(rattedInAccountMock).clearContacts();
    }

    @Test
    void whenReceiveHarassmentComplaint_thenSabotage() {
        Hamster hamsterMock = mock(Hamster.class);
        chinchilla.representNewHamster(hamsterMock);

        chinchilla.receiveHarassmentComplaint();

        assertTrue(chinchilla.getCelebrityRepresented().isEmpty());
        assertEquals(HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER + 1, chinchilla.getUnavailabilityTurnNumber());
    }

    @Test
    void whenPromoteMovie_thenApplyHamstagramCelebrityBonus() {
        List<Movie> movies = new ArrayList<>();
        chinchilla.representNewHamster(hamsterMock);

        chinchilla.promoteMovie(movies);

        verify(hamsterMock).applyBonusFromChinchilla(movies);
    }
}
