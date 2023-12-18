package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.bank.BankAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RatTest {
    private static final String NAME = "TestName";
    private static final String CONTACT_NAME = "TestContactName";
    private static final String CLIENT_NAME = "ClientName";
    private static final String ACTION_CODE = "SC";
    private static final int TURN_NUMBER_LAWSUIT_RECEIVED = 1;
    private static final int SALARY = 250;
    private static final Type CHARACTER_TYPE_RAT = Type.RAT;
    private static final int INVALID_BANK_BALANCE = 0;
    private static final int VALID_REPUTATION = 15;
    private static final RattedInStatus BUSY_STATUS = RattedInStatus.BUSY;
    private static final RattedInStatus OPEN_TO_WORK = RattedInStatus.OPEN_TO_WORK;

    private BankAccount validBankAccount;
    private BankAccount invalidBankAccount;
    private RattedInAccount rattedInAccount;
    private RattedInAccount rattedInAccountOpenToWork;
    private RattedInAccount rattedInAccountBusy;
    private BankAccount bankAccountMock;
    private RattedInAccount rattedInAccountMock;

    @BeforeEach
    void initMocks() {
        rattedInAccountMock = mock(RattedInAccount.class);
        bankAccountMock = mock(BankAccount.class);
    }

    @BeforeEach
    void initRat() {
        RattedInAccountFactory rattedInAccountFactory = new RattedInAccountFactory();
        rattedInAccount = rattedInAccountFactory.create(NAME, RattedInStatus.NA);
        rattedInAccountOpenToWork = rattedInAccountFactory.create(NAME, OPEN_TO_WORK);
        rattedInAccountBusy = rattedInAccountFactory.create(NAME, BUSY_STATUS);
    }

    @BeforeEach
    void initBankAccountFactory() {
        BankAccountFactory bankAccountFactory = new BankAccountFactory();
        validBankAccount = bankAccountFactory.create();
        invalidBankAccount = new BankAccount(INVALID_BANK_BALANCE);
    }

    @Test
    void givenReputationAndBankAccountAboveMinimal_whenShouldBeEliminated_thenReturnFalse() {
        Rat validRat = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);

        boolean isRatShouldBeEliminated = validRat.eliminateIfValidConditions();

        assertFalse(isRatShouldBeEliminated);
    }

    @Test
    void givenReputationBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        int invalidReputation = 14;
        Rat invalidRat = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, invalidReputation, validBankAccount, rattedInAccount);

        boolean isRatShouldBeEliminated = invalidRat.eliminateIfValidConditions();

        assertTrue(isRatShouldBeEliminated);
    }

    @Test
    void givenBankBalanceBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        Rat invalidRat = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, invalidBankAccount, rattedInAccount);

        boolean isRatShouldBeEliminated = invalidRat.eliminateIfValidConditions();

        assertTrue(isRatShouldBeEliminated);
    }

    @Test
    void whenReceivePayment_thenCallReceivePayment() {
        Rat ratWithMockedBankAccount = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, bankAccountMock, rattedInAccount);
        float amount = 100;

        ratWithMockedBankAccount.receivePayment(amount);

        verify(bankAccountMock).receivePayment(amount);
    }

    @Test
    void whenContactRattedInRequestIsReceived_thenAccept() {
        RattedInUser receiver = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);
        RattedInUser sender = new Rat(CONTACT_NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);

        boolean isAccepted = receiver.acceptRattedInRequest(sender);

        assertTrue(isAccepted);
    }

    @Test
    void whenAddRattedInContact_thenCallAddContact() {
        RattedInUser ratWithMockedRattedInAccount = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountMock);
        RattedInUser contact = new Rat(CONTACT_NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);

        ratWithMockedRattedInAccount.addRattedInContact(contact);

        verify(rattedInAccountMock).addContact(contact);
    }

    @Test
    void whenRemoveRattedInContact_thenCallRemoveContact() {
        Rat ratWithMockedRattedInAccount = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountMock);
        RattedInUser contact = new Rat(CONTACT_NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);

        ratWithMockedRattedInAccount.removeRattedInContact(contact);

        verify(rattedInAccountMock).removeContact(contact);
    }

    @Test
    void whenReceiveHarassmentComplaint_thenLoseAllRatedInContacts() {
        Rat ratWithMockedRattedInAccount = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountMock);

        ratWithMockedRattedInAccount.receiveHarassmentComplaint();

        verify(rattedInAccountMock).clearContacts();
    }

    @Test
    void givenRatIsOpenToWorkAndHasNoLawsuits_whenAskedIfAvailable_thenReturnAvailable() {
        Rat ratOpenToWork = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountOpenToWork);

        boolean isRatAvailable = ratOpenToWork.isAvailable();

        assertTrue(isRatAvailable);
    }

    @Test
    void givenRatIsOpenToWorkAndHasNoLawsuits_whenAcceptContractOfClient_thenRattedInAccountStatusIsBusyWithClient() {
        Rat ratOpenToWork = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountOpenToWork);

        ratOpenToWork.acceptContract(CLIENT_NAME);

        assertEquals(ratOpenToWork.getClient().get(), CLIENT_NAME);
        assertEquals(ratOpenToWork.getRattedInAccount().getRattedInStatus(), BUSY_STATUS);
    }

    @Test
    void givenRatHasClient_whenResolvedLawsuitsOfClient_thenRattedInAccountStatusIsOpenToWorkAndClientIsNone() {
        Rat ratBusy = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountBusy);

        ratBusy.releaseContract();

        assertTrue(ratBusy.getClient().isEmpty());
        assertEquals(ratBusy.getRattedInAccount().getRattedInStatus(), OPEN_TO_WORK);
    }

    @Test
    void givenRatHasPossibleLaywer_whenRatHasToFindLayer_thenSelectLawyerCalled() {
        Rat validRat = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccount);
        Character contact = new Rat(CONTACT_NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountOpenToWork);
        List<Character> characterList = new ArrayList<>();
        characterList.add(contact);
        Characters characters = new Characters(characterList);
        validRat.addRattedInContact((RattedInUser) contact);

        validRat.findLawyer(characters);

        assertEquals(validRat.lawyer.get(), CONTACT_NAME);
    }

    @Test
    void givenRatHasClient_whenRatReceivesLawsuit_thenClientLostLawyer() {
        Rat ratBusy = new Rat(NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountBusy);
        Character contact = new Rat(CONTACT_NAME, CHARACTER_TYPE_RAT, SALARY, VALID_REPUTATION, validBankAccount, rattedInAccountOpenToWork);
        List<Character> characterList = new ArrayList<>();
        characterList.add(contact);
        Characters characters = new Characters(characterList);
        ratBusy.setClient(Optional.of(CONTACT_NAME));
        contact.selectLawyer(ratBusy);

        ratBusy.receiveLawsuit(ACTION_CODE, TURN_NUMBER_LAWSUIT_RECEIVED, characters, new ArrayList<>());

        assertTrue(contact.getLawyer().isEmpty());
    }
}