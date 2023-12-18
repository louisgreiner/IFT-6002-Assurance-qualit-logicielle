package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccount;
import ca.ulaval.glo4002.game.domain.bank.BankAccountFactory;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HamsterTest {
    private static final float INVALID_BANK_BALANCE = 0;
    private static final String NAME = "TestName";
    private static final String ACTION_CODE = "RC";
    private static final int TURN_NUMBER_LAWSUIT_RECEIVED = 1;
    private static final int SALARY = 250;
    private static final Type CHARACTER_TYPE = Type.HAMSTER;
    private static final int VALID_REPUTATION = 75;
    private static final int UNAVAILABILITY_TURN_NUMBER = 10;
    private static final float HARASSMENT_COMPLAINT_HAMSTAGRAM_FOLLOWERS_LOSS_RATE = 0.7F;
    private static final int HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER = 3;
    private static final int REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN = 20000;
    private static final int REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER = 2;
    private static final float SCANDAL_HAMSTAGRAM_FOLLOWERS_LOSS_RATE = 0.4F;
    private static final int SCANDAL_UNAVAILABILITY_TURN_NUMBER = 2;

    private BankAccount validBankAccount;
    private BankAccount invalidBankAccount;
    private Hamster hamster;
    private BankAccount bankAccountMock;
    private HamstagramAccount hamstagramAccountMock;
    private HamstagramAccount hamstagramAccount;
    private CharacterFactory characterFactory;
    private Movie movieMock;

    @BeforeEach
    void initMocks() {
        bankAccountMock = mock(BankAccount.class);
        hamstagramAccountMock = mock(HamstagramAccount.class);
        movieMock = mock(Movie.class);
    }

    @BeforeEach
    void initHamster() {
        BankAccountFactory bankAccountFactory = new BankAccountFactory();
        validBankAccount = bankAccountFactory.create();
        invalidBankAccount = new BankAccount(INVALID_BANK_BALANCE);

        HamstagramAccountFactory hamstagramAccountFactory = new HamstagramAccountFactory();
        hamstagramAccount = hamstagramAccountFactory.create(NAME);

        hamster = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);
        characterFactory = new CharacterFactory();
    }

    @Test
    void whenUpdateNextTurn_thenUpdateReputation() {
        int reputationLossByTurn = 6;
        int expectedReputation = hamster.getReputation() - reputationLossByTurn;

        hamster.updateNextTurn();

        assertEquals(expectedReputation, hamster.getReputation());
    }

    @Test
    void givenwhenUpdateNextTurn_thenCallBankAccountUpdateNextTurn() {
        Hamster hamsterWithMockedBankAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount);

        hamsterWithMockedBankAccount.updateNextTurn();

        verify(bankAccountMock).updateNextTurn();
    }

    @Test
    void whenUpdateHamstagramAccountsNextTurn_thenCallHamstagramAccountUpdateNextTurn() {
        Hamster hamsterWithMockedHamstagramAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);

        hamsterWithMockedHamstagramAccount.updateHamstagramAccountsNextTurn();

        verify(hamstagramAccountMock).updateNextTurn();
    }

    @Test
    void whenPayChinchillaSalary_thenCallBankAccountPay() {
        Hamster hamsterWithMockedBankAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount);
        Chinchilla chinchilla = (Chinchilla) characterFactory.create(NAME, Type.CHINCHILLA, SALARY);
        hamsterWithMockedBankAccount.handleHamstagramRequestFrom(chinchilla);
        hamsterWithMockedBankAccount.chooseBestHamstagramRequest();

        hamsterWithMockedBankAccount.payManager();

        verify(bankAccountMock).pay(chinchilla, SALARY);
    }

    @Test
    void givenReputationAndBankAccountAboveMinimal_whenShouldBeEliminated_thenReturnFalse() {
        Hamster validHamster = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccount);

        boolean isHamsterShouldBeEliminated = validHamster.eliminateIfValidConditions();

        assertFalse(isHamsterShouldBeEliminated);
    }

    @Test
    void givenReputationBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        int invalidReputation = 14;
        Hamster invalidHamster = new Hamster(NAME, CHARACTER_TYPE, SALARY, invalidReputation, validBankAccount, hamstagramAccount);

        boolean isHamsterShouldBeEliminated = invalidHamster.eliminateIfValidConditions();

        assertTrue(isHamsterShouldBeEliminated);
    }

    @Test
    void givenBankBalanceBelowMinimal_whenShouldBeEliminated_thenReturnTrue() {
        Hamster invalidHamster = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, invalidBankAccount, hamstagramAccount);

        boolean isHamsterShouldBeEliminated = invalidHamster.eliminateIfValidConditions();

        assertTrue(isHamsterShouldBeEliminated);
    }

    @Test
    void whenReceivePayment_thenCallReceivePayment() {
        Hamster hamsterWithMockedBankAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount);
        float amount = 100;

        hamsterWithMockedBankAccount.receivePayment(amount);

        verify(bankAccountMock).receivePayment(amount);
    }

    @Test
    void givenHasChinchilla_whenShouldBeEliminated_thenCallRemoveHamsterInRepresent() {
        Hamster hamsterWithMockedBankAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount);
        Chinchilla chinchillaMock = mock(Chinchilla.class);
        when(chinchillaMock.getReputation()).thenReturn(75);
        when(chinchillaMock.getName()).thenReturn("ChinchillaName");
        hamsterWithMockedBankAccount.handleHamstagramRequestFrom(chinchillaMock);
        hamsterWithMockedBankAccount.chooseBestHamstagramRequest();
        when(bankAccountMock.shouldBeEliminated()).thenReturn(true);

        hamsterWithMockedBankAccount.eliminateIfValidConditions();

        verify(chinchillaMock).removeCelebrityRepresented(hamsterWithMockedBankAccount);
    }

    @Test
    void givenNoChinchilla_whenHasNoChinchilla_thenReturnTrue() {
        boolean isRepresentedByEmpty = hamster.hasNoChinchilla();

        assertTrue(isRepresentedByEmpty);
    }

    @Test
    void givenHasChinchilla_whenHasNoChinchilla_thenReturnFalse() {
        Chinchilla chinchilla = (Chinchilla) characterFactory.create(NAME, Type.CHINCHILLA, SALARY);
        hamster.handleHamstagramRequestFrom(chinchilla);
        hamster.chooseBestHamstagramRequest();

        boolean isRepresentedByEmpty = hamster.hasNoChinchilla();

        assertFalse(isRepresentedByEmpty);
    }

    @Test
    void givenRepresentedByAChinchilla_whenRemoveRepresentedByChinchilla_thenIsNotRepresentedByChinchilla() {
        Chinchilla chinchilla = (Chinchilla) characterFactory.create(NAME, Type.CHINCHILLA, SALARY);
        hamster.handleHamstagramRequestFrom(chinchilla);
        hamster.chooseBestHamstagramRequest();

        hamster.removeManager();

        boolean hasNoChinchilla = hamster.hasNoChinchilla();
        assertTrue(hasNoChinchilla);
    }

    @Test
    void givenUnavailability_whenDecrementUnavailability_thenUnavailabilityIsDecremented() {
        hamster.sabotage(UNAVAILABILITY_TURN_NUMBER);

        hamster.decrementUnavailability();

        int expectedUnavailability = UNAVAILABILITY_TURN_NUMBER - 1;
        assertEquals(expectedUnavailability, hamster.getUnavailabilityTurnNumber());
    }

    @Test
    void givenUnavailabilityIsZero_whenDecrementUnavailability_thenUnavailabilityIsNotDecremented() {
        hamster.decrementUnavailability();

        int expectedUnavailability = 0;
        assertEquals(expectedUnavailability, hamster.getUnavailabilityTurnNumber());
    }

    @Test
    void whenSabotage_thenUpdateUnavailabilityTurnNumber() {
        hamster.sabotage(UNAVAILABILITY_TURN_NUMBER);

        assertEquals(UNAVAILABILITY_TURN_NUMBER, hamster.getUnavailabilityTurnNumber());
    }

    @Test
    void whenSabotage_thenCanBeCastIsFalse() {
        hamster.sabotage(UNAVAILABILITY_TURN_NUMBER);

        assertFalse(hamster.canBeCast());
    }

    @Test
    void whenGiveBonus_thenHasBonusIsTrue() {
        hamster.giveBonus();

        assertTrue(hamster.getHasBonus());
    }

    @Test
    void givenHasBonusFalse_whenApplyMovieBonus_thenNoInteractionWithMovie() {
        List<Movie> moviesWithMock = new ArrayList<>();
        moviesWithMock.add(movieMock);

        hamster.applyMovieBonus(moviesWithMock);

        verifyNoInteractions(movieMock);
    }

    @Test
    void givenHasBonusAndHamsterInCasting_whenApplyBonusMovieBonus_thenMovieApplyBonus() {
        List<Movie> movies = List.of(movieMock);
        hamster.giveBonus();
        List<String> hamsters = List.of(hamster.getName());
        when(movieMock.getCasting()).thenReturn(hamsters);

        hamster.applyMovieBonus(movies);

        verify(movieMock).getCasting();
        verify(movieMock).applyBonus();
    }

    @Test
    void givenHasBonusAndHamsterInCasting_whenApplyBonusMovieBonus_thenHasBonusFalse() {
        List<Movie> movies = List.of(movieMock);
        hamster.giveBonus();
        List<String> hamsters = List.of(hamster.getName());
        when(movieMock.getCasting()).thenReturn(hamsters);

        hamster.applyMovieBonus(movies);

        assertFalse(hamster.getHasBonus());
    }

    @Test
    void givenHamsterInCasting_whenApplyBonusFromChinchilla_thenMovieApplyBonus() {
        List<Movie> movies = List.of(movieMock);
        hamster.giveBonus();
        List<String> hamsters = List.of(hamster.getName());
        when(movieMock.getCasting()).thenReturn(hamsters);

        hamster.applyBonusFromChinchilla(movies);

        verify(movieMock).applyBonus();
    }

    @Test
    void whenGetFollowersSalaryRatio_thenReturnZero() {
        float followersSalaryRatio = hamster.getFollowersSalaryRatio();

        assertEquals(0, followersSalaryRatio);
    }

    @Test
    void whenReceiveHarassmentComplaint_thenLoseFollowersFromRate() {
        Hamster hamsterWithMockedHamstagramAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);

        hamsterWithMockedHamstagramAccount.receiveHarassmentComplaint();

        verify(hamstagramAccountMock).deductFollowersNumberFromRate(HARASSMENT_COMPLAINT_HAMSTAGRAM_FOLLOWERS_LOSS_RATE);
    }

    @Test
    void whenReceiveHarassmentComplaint_thenSabotage() {
        hamster.receiveHarassmentComplaint();

        assertEquals(HARASSMENT_COMPLAINT_UNAVAILABILITY_TURN_NUMBER, hamster.getUnavailabilityTurnNumber());
        assertFalse(hamster.canBeCast());
    }

    @Test
    void whenDoRealityShow_thenAddHamstagramFollowers() {
        Hamster hamsterWithMockedHamstagramAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);

        hamsterWithMockedHamstagramAccount.doRealityShow();

        verify(hamstagramAccountMock).addFollowers(REALITY_SHOW_HAMSTAGRAM_FOLLOWERS_GAIN);
    }

    @Test
    void whenDoRealityShow_thenSabotage() {
        hamster.doRealityShow();

        assertEquals(REALITY_SHOW_UNAVAILABILITY_TURN_NUMBER, hamster.getUnavailabilityTurnNumber());
        assertFalse(hamster.canBeCast());
    }

    @Test
    void whenReceiveScandal_thenLoseFollowersFromRateFromRate() {
        Hamster hamsterWithMockedHamstagramAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);

        hamsterWithMockedHamstagramAccount.receiveScandal();

        verify(hamstagramAccountMock).deductFollowersNumberFromRate(SCANDAL_HAMSTAGRAM_FOLLOWERS_LOSS_RATE);

    }

    @Test
    void whenReceiveScandal_thenSabotage() {
        hamster.receiveScandal();

        assertEquals(SCANDAL_UNAVAILABILITY_TURN_NUMBER, hamster.getUnavailabilityTurnNumber());
        assertFalse(hamster.canBeCast());
    }

    @Test
    void whenPromoteMovie_thenGiveBonus() {
        List<Movie> movies = new ArrayList<>();
        hamster.promoteMovie(movies);

        boolean expectedHasBonus = hamster.getHasBonus();
        assertTrue(expectedHasBonus);
    }

    @Test
    void whenPromoteMovie_thenApplyMovieBonus() {
        List<Movie> movies = List.of(movieMock);
        hamster.giveBonus();
        List<String> hamsters = List.of(hamster.getName());
        when(movieMock.getCasting()).thenReturn(hamsters);

        hamster.promoteMovie(movies);

        verify(movieMock).getCasting();
        verify(movieMock).applyBonus();
        assertFalse(hamster.getHasBonus());
    }

    @Test
    void givenHamsterIsInMovies_whenHamsterReceiveLawsuit_thenHamsterIsNotInMovies() {
        List<Movie> listMovieMock = new ArrayList<>();
        listMovieMock.add(movieMock);
        hamster.receiveLawsuit(ACTION_CODE, TURN_NUMBER_LAWSUIT_RECEIVED, new Characters(), listMovieMock);

        verify(movieMock).removeHamster(hamster.getName());
    }

    @Test
    void givenHamsterHasManager_whenHamsterReveiveLawsuit_thenLaywerIsFound() {
        HamstagramManager hamstagramManagerMock = mock(HamstagramManager.class);
        Hamster hamsterWithManager = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock, hamstagramManagerMock);
        Characters characters = new Characters();

        hamsterWithManager.findLawyer(characters);

        verify(hamstagramManagerMock).findLawyerForHamsters(characters);
    }

    @Test
    void whenHamsterStartFilming_thenIsFilmingAndCanNotBeCast() {
        hamster.startFilming();

        assertTrue(hamster.getIsFilming());
        assertFalse(hamster.canBeCast());
    }

    @Test
    void whenHamsterEndFilming_thenIsNotFilmingAndCanBeCast() {
        hamster.endFilming();

        assertFalse(hamster.getIsFilming());
        assertTrue(hamster.canBeCast());
    }

    @Test
    void whenReceiveSalary_thenBankAccountReceiveSalary() {
        Hamster hamsterWithMockedBankAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, bankAccountMock, hamstagramAccount);

        hamsterWithMockedBankAccount.receiveSalary();

        verify(bankAccountMock).receiveSalary(argThat(salary -> salary.getSalary() == SALARY));
    }

    @Test
    void whenGetHamsterFollowerNumber_thenHamsterFollowerNumberIsReturned() {
        Hamster hamsterWithMockedHamstagramAccount = new Hamster(NAME, CHARACTER_TYPE, SALARY, VALID_REPUTATION, validBankAccount, hamstagramAccountMock);

        hamsterWithMockedHamstagramAccount.getHamstagramFollowersNumber();

        verify(hamstagramAccountMock).getFollowersNumber();
    }

    @Test
    void givenHamsterHasLawsuits_whenHamsterReceiveLawsuit_thenHamsterCannotBeCast() {
        List<Movie> listMovieMock = new ArrayList<>();
        listMovieMock.add(movieMock);

        hamster.receiveLawsuit(ACTION_CODE, TURN_NUMBER_LAWSUIT_RECEIVED, new Characters(), listMovieMock);

        assertFalse(hamster.canBeCast());
    }
}