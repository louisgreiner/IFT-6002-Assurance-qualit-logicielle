package ca.ulaval.glo4002.game.domain.character.exceptions;

public class InvalidCharacterSalaryException extends RuntimeException {
    public InvalidCharacterSalaryException(int salary) {
        super("Salary cannot be negative : " + salary);
    }
}
