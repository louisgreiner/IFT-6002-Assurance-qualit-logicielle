package ca.ulaval.glo4002.game.domain.bank;

import ca.ulaval.glo4002.game.domain.character.Salary;

public class BankAccount {
    public static final float BANK_BALANCE_LOSS_BY_TURN = 100;
    public static final float MINIMAL_BANK_BALANCE = 0;

    private float balance;

    public BankAccount(float bankBalance) {
        this.balance = bankBalance;
    }

    public float getBalance() {
        return balance;
    }

    public void updateNextTurn() {
        balance -= BANK_BALANCE_LOSS_BY_TURN;
    }

    public boolean shouldBeEliminated() {
        return balance <= MINIMAL_BANK_BALANCE;
    }

    public void pay(Payable payable, float amount) {
        if (amount > balance) {
            payable.receivePayment(balance);
            balance = 0;
        } else {
            payable.receivePayment(amount);
            balance -= amount;
        }
    }

    public void receivePayment(float amount) {
        balance += amount;
    }

    public void receiveSalary(Salary salary) {
        balance += salary.getSalary();
    }
}
