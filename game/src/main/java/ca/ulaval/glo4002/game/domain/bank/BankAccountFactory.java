package ca.ulaval.glo4002.game.domain.bank;

public class BankAccountFactory {
    private static final float DEFAULT_BANK_BALANCE = 1000;

    public BankAccount create() {
        return new BankAccount(DEFAULT_BANK_BALANCE);
    }
}
