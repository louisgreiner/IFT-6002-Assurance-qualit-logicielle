package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TypeTest {
    private static final String INVALID_TYPE = "tortue";

    @Test
    void givenInvalidTypeString_whenFromString_thenThrowException() {
        assertThrows(InvalidCharacterTypeException.class, () -> Type.fromString(INVALID_TYPE));
    }
}