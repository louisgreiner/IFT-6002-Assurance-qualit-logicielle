package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterSalaryException;

public class Salary {
    private final int salary;

    public Salary(int salary) {
        if (salary <= 0) {
            throw new InvalidCharacterSalaryException(salary);
        }
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
}
