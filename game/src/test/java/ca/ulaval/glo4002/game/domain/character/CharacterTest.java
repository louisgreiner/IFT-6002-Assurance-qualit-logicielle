package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterTest {
    private static final int SAMPLE_SALARY = 190;
    private static final String LAWYER_NAME = "Lawyer";
    private static final float AMOUNT = 100.5F;
    private static final int GREATER_REPUTATION = 1004;
    private static final int LOWER_REPUTATION = 16;
    private static final String NAME = "TestName";
    private static final int SALARY = 250;
    private static final Type CHARACTER_TYPE = Type.RAT;
    private static final int DEFAULT_REPUTATION = 40;
    private static final String SAMPLE_ACTION_CODE = "FR";
    private static final int SAMPLE_TURN_NUMBER = 3;

    private Character character;
    private BankAccount bankAccountMock;
    private Characters characters;
    private RattedInAccount rattedInAccountMock;
    private Rat lawyerMock;

    private Character createCharacter(int reputation) {
        return new Character(NAME, CHARACTER_TYPE, SALARY, reputation, bankAccountMock) {
            @Override
            public boolean eliminateIfValidConditions() {
                return false;
            }

            @Override
            protected void findLawyer(Characters characters) {
                throw new RuntimeException("Method not implemented.");
            }

            @Override
            public void receiveHarassmentComplaint() {

            }
        };
    }

    @BeforeEach
    void initCharacter() {
        bankAccountMock = mock(BankAccount.class);
        character = createCharacter(DEFAULT_REPUTATION);
        characters = new Characters();
    }

    @BeforeEach
    void initRattedInAccount() {
        rattedInAccountMock = mock(RattedInAccount.class);
    }

    @BeforeEach
    void initRatMock() {
        lawyerMock = mock(Rat.class);
        when(lawyerMock.getName()).thenReturn(LAWYER_NAME);
    }

    @Test
    void givenSpreadGossip_whenGetHasSpreadGossip_thenReturnTrue() {
        character.spreadGossip();

        assertTrue(character.hasSpreadGossip);
    }

    @Test
    void givenNoSpreadGossip_whenGetHasSpreadGossip_thenReturnFalse() {
        assertFalse(character.hasSpreadGossip);
    }

    @Test
    void whenUpdateNextTurn_thenUpdateReputation() {
        int reputationLossByTurn = 6;
        int expectedReputation = character.getReputation() - reputationLossByTurn;

        character.updateNextTurn();

        assertEquals(expectedReputation, character.getReputation());
    }

    @Test
    void whenUpdateNextTurn_thenCallUpdateNextTurn() {
        character.updateNextTurn();

        verify(bankAccountMock).updateNextTurn();
    }

    @Test
    void whenReceivePayment_thenCallReceivePayment() {
        character.receivePayment(AMOUNT);

        verify(bankAccountMock).receivePayment(AMOUNT);
    }

    @Test
    void givenCharacterWithGreaterReputation_whenHasGreaterOrEqualReputationThan_thenReturnTrue() {
        Character characterWithGreaterReputation = createCharacter(GREATER_REPUTATION);

        boolean result = character.hasGreaterOrEqualReputationThan(characterWithGreaterReputation);

        assertFalse(result);
    }

    @Test
    void givenCharacterWithEqualReputation_whenHasGreaterOrEqualReputationThan_thenReturnTrue() {
        Character characterWithEqualReputation = createCharacter(DEFAULT_REPUTATION);

        boolean result = character.hasGreaterOrEqualReputationThan(characterWithEqualReputation);

        assertTrue(result);
    }

    @Test
    void givenCharacterWithLowerReputation_whenHasGreaterOrEqualReputationThan_thenReturnFalse() {
        Character characterWithLowerReputation = createCharacter(LOWER_REPUTATION);

        boolean result = character.hasGreaterOrEqualReputationThan(characterWithLowerReputation);

        assertTrue(result);
    }

    @Test
    void whenGetRepresentedBy_thenReturnEmptyOptional() {
        Optional<HamstagramManager> representedBy = character.getManager();

        assertTrue(representedBy.isEmpty());
    }

    @Test
    void whenGetRepresent_thenReturnEmptyList() {
        List<HamstagramCelebrity> represent = character.getCelebrityRepresented();

        assertTrue(represent.isEmpty());
    }

    @Test
    void givenNoGossip_whenSufferFromGossip_thenReputationDecreaseBy5() {
        int reputationLoss = 5;
        int expectedReputation = character.getReputation() - reputationLoss;

        character.sufferFromGossip();

        assertEquals(expectedReputation, character.getReputation());
    }

    @Test
    void givenOneGossip_whenSufferFromGossip_thenReputationDecreaseBy10() {
        character.sufferFromGossip();
        int reputationLoss = 10;
        int expectedReputation = character.getReputation() - reputationLoss;

        character.sufferFromGossip();

        assertEquals(expectedReputation, character.getReputation());
    }

    @Test
    void givenTwoGossips_whenSufferFromGossip_thenReputationDecreaseBy15() {
        character.sufferFromGossip();
        character.sufferFromGossip();
        int reputationLoss = 15;
        int expectedReputation = character.getReputation() - reputationLoss;

        character.sufferFromGossip();

        assertEquals(expectedReputation, character.getReputation());
    }

    @Test
    void givenThreeGossips_whenSufferFromGossip_thenReputationDecreaseBy15() {
        character.sufferFromGossip();
        character.sufferFromGossip();
        character.sufferFromGossip();
        int reputationLoss = 15;
        int expectedReputation = character.getReputation() - reputationLoss;

        character.sufferFromGossip();

        assertEquals(expectedReputation, character.getReputation());
    }

    @Test
    void givenLawyer_whenPayLawyer_thenCallBankAccountPay() {
        when(lawyerMock.getSalary()).thenReturn(SAMPLE_SALARY);
        character.selectLawyer(lawyerMock);

        character.payLawyer(new Characters(List.of(lawyerMock)));

        verify(bankAccountMock).pay(lawyerMock, SAMPLE_SALARY);
    }

    @Test
    void givenNoLawyer_whenPayLawyer_thenDontCallBankAccountPay() {
        when(lawyerMock.getSalary()).thenReturn(SAMPLE_SALARY);

        character.payLawyer(new Characters(List.of(lawyerMock)));

        verify(bankAccountMock, never()).pay(lawyerMock, SAMPLE_SALARY);
    }

    @Test
    void whenReceiveLawsuit_thenLawsuitIsAdded() {
        character.receiveLawsuit(SAMPLE_ACTION_CODE, SAMPLE_TURN_NUMBER, new Characters(), new ArrayList<>());

        assertFalse(character.getLawsuits().isEmpty());
    }

    @Test
    void givenLawsuitAndNoLawyer_whenResolveLawsuits_thenDoNothing() {
        character.receiveLawsuit(SAMPLE_ACTION_CODE, SAMPLE_TURN_NUMBER, new Characters(), new ArrayList<>());

        character.resolveLawsuits(new Characters());

        assertFalse(character.getLawsuits().isEmpty());
    }

    @Test
    void givenLawsuitAndLawyer_whenResolveLawsuits_thenLawsuitIsRemoved() {
        character.receiveLawsuit(SAMPLE_ACTION_CODE, SAMPLE_TURN_NUMBER, new Characters(), new ArrayList<>());
        character.selectLawyer(lawyerMock);

        character.resolveLawsuits(new Characters());

        assertTrue(character.getLawsuits().isEmpty());
    }

    @Test
    void whenRemoveLawyer_thenLawyerIsRemoved() {
        character.selectLawyer(lawyerMock);

        character.removeLawyer(new Characters());

        assertTrue(character.getLawyer().isEmpty());
    }

    @Test
    void whenRemoveLawyer_thenCallReleaseContract() {
        character.selectLawyer(lawyerMock);

        character.removeLawyer(new Characters(List.of(lawyerMock)));

        verify(lawyerMock).releaseContract();
    }

    @Test
    void givenLawsuitsEmpty_whenChooseLawyer_thenDoNothing() {
        assertDoesNotThrow(() -> character.chooseLawyer(characters));
    }

    @Test
    void givenLawyer_whenChooseLawyer_thenDoNothing() {
        character.selectLawyer(lawyerMock);

        assertDoesNotThrow(() -> character.chooseLawyer(characters));
    }

    @Test
    void givenNoLawyerAndLawsuit_whenChooseLawyer_thenCallFindLawyer() {
        character.receiveLawsuit(SAMPLE_ACTION_CODE, SAMPLE_TURN_NUMBER, new Characters(), new ArrayList<>());

        assertThrows(RuntimeException.class, () -> character.chooseLawyer(characters));
    }

    @Test
    void givenNoRattedInContact_whenFindPossibleLawyers_thenReturnEmptyList() {
        when(rattedInAccountMock.getContacts()).thenReturn(new ArrayList<>());

        Optional<Rat> possibleLawyer = character.findPossibleLawyer(characters, rattedInAccountMock);

        assertTrue(possibleLawyer.isEmpty());
    }

    @Test
    void givenARattedInContact_whenFindPossibleLawyers_thenReturnLawyer() {
        when(rattedInAccountMock.getContacts()).thenReturn(List.of(lawyerMock));
        when(lawyerMock.isAvailable()).thenReturn(true);
        characters.add(lawyerMock);

        Optional<Rat> possibleLawyer = character.findPossibleLawyer(characters, rattedInAccountMock);

        if (possibleLawyer.isEmpty()) {
            fail();
        }
        assertEquals(lawyerMock, possibleLawyer.get());
    }

    @Test
    void givenARattedInContactAndLawyerNotInCharacters_whenFindPossibleLawyers_thenPossibleLawyerIsEmpty() {
        when(rattedInAccountMock.getContacts()).thenReturn(List.of(lawyerMock));

        Optional<Rat> possibleLawyer = character.findPossibleLawyer(characters, rattedInAccountMock);

        assertTrue(possibleLawyer.isEmpty());
    }

    @Test
    void givenTwoLawyers_whenFindPossibleLawyers_thenReturnSortedList() {
        Rat smallReputationRatMock = initValidRatMock(LOWER_REPUTATION, NAME);
        Rat highReputationRatMock = initValidRatMock(GREATER_REPUTATION, LAWYER_NAME);
        characters.addAll(List.of(smallReputationRatMock, highReputationRatMock));
        when(rattedInAccountMock.getContacts()).thenReturn(List.of(smallReputationRatMock, highReputationRatMock));

        Optional<Rat> possibleLawyer = character.findPossibleLawyer(characters, rattedInAccountMock);

        if (possibleLawyer.isEmpty()) {
            fail();
        }
        assertEquals(highReputationRatMock, possibleLawyer.get());
    }

    private Rat initValidRatMock(int reputation, String name) {
        Rat ratMock = mock(Rat.class);
        when(ratMock.getReputation()).thenReturn(reputation);
        when(ratMock.getName()).thenReturn(name);
        when(ratMock.isAvailable()).thenReturn(true);
        return ratMock;
    }
}