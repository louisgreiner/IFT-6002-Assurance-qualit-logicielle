package ca.ulaval.glo4002.game.domain.bank;

public interface Payable {
    int getSalary();

    void receivePayment(float balance);
}
