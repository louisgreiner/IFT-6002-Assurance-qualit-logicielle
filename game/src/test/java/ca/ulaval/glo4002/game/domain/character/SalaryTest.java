package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterSalaryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SalaryTest {
    public static final int INVALID_SALARY = 0;

    @Test
    void whenGetSalary_thenReturnSalary() {
        int salaryValueExpected = 40;
        Salary salary = new Salary(salaryValueExpected);

        int salaryValue = salary.getSalary();

        assertEquals(salaryValueExpected, salaryValue);
    }

    @Test
    void givenInvalidSalary_whenConstruct_thenThrowException() {
        assertThrows(InvalidCharacterSalaryException.class, () -> new Salary(INVALID_SALARY));
    }
}