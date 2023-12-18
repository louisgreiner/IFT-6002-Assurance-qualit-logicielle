package ca.ulaval.glo4002.game.domain.bank;

import ca.ulaval.glo4002.game.domain.character.HamstagramManager;
import ca.ulaval.glo4002.game.domain.character.Salary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BankAccountTest {
    private static final float BANK_BALANCE = 1000;
    private static final float INSUFFICIENT_BALANCE = -1;

    private BankAccount bankAccount;
    private HamstagramManager hamstagramManagerMock;

    @BeforeEach
    void initHamstagramManagerMock() {
        hamstagramManagerMock = mock(HamstagramManager.class);
    }

    @BeforeEach
    void initBankAccount() {
        bankAccount = new BankAccount(BANK_BALANCE);
    }

    @Test
    void whenGetBalance_thenReturnBalance() {
        float balance = bankAccount.getBalance();
        assertEquals(BANK_BALANCE, balance);
    }

    @Test
    void whenUpdateNextTurn_thenBalanceDecrease() {
        float expectedBankBalance = BANK_BALANCE - BankAccount.BANK_BALANCE_LOSS_BY_TURN;

        bankAccount.updateNextTurn();

        assertEquals(expectedBankBalance, bankAccount.getBalance());
    }

    @Test
    void givenInsufficientBalance_whenShouldBeEliminated_thenReturnTrue() {
        bankAccount = new BankAccount(INSUFFICIENT_BALANCE);

        boolean shouldBeEliminated = bankAccount.shouldBeEliminated();

        assertTrue(shouldBeEliminated);
    }

    @Test
    void whenReceivePayment_thenBalanceIncrease() {
        float amount = 100;
        float expectedBankBalance = BANK_BALANCE + amount;

        bankAccount.receivePayment(amount);

        assertEquals(expectedBankBalance, bankAccount.getBalance());
    }

    @Test
    void whenPay_thenBalanceDecrease() {
        float amount = 100;
        float expectedBankBalance = BANK_BALANCE - amount;

        bankAccount.pay(hamstagramManagerMock, amount);

        assertEquals(expectedBankBalance, bankAccount.getBalance());
    }

    @Test
    void givenInsufficientBalance_whenPay_thenBalanceIsZero() {
        float amount = 10000;
        float expectedBankBalance = 0;

        bankAccount.pay(hamstagramManagerMock, amount);

        assertEquals(expectedBankBalance, bankAccount.getBalance());
    }

    @Test
    void whenPay_thenCallReceivePayment() {
        float amount = 100;

        bankAccount.pay(hamstagramManagerMock, amount);

        verify(hamstagramManagerMock).receivePayment(amount);
    }

    @Test
    void whenReceiveSalary_thenSalaryIsUpdated() {
        Salary salary = new Salary(500);
        float expectedBalance = bankAccount.getBalance() + salary.getSalary();

        bankAccount.receiveSalary(salary);

        assertEquals(expectedBalance, bankAccount.getBalance());
    }
}