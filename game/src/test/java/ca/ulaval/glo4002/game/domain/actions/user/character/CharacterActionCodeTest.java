package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.user.character.exceptions.InvalidActionCodeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharacterActionCodeTest {
    private static final String VALID_CHARACTER_ACTION_CODE_STRING = "PL";
    private static final String INVALID_CHARACTER_ACTION_CODE_STRING = "invalid";
    private static final CharacterActionCode VALID_CHARACTER_ACTION_CODE = CharacterActionCode.PL;

    @Test
    void givenAValidActionCode_whenFromString_thenReturnActionCode() {
        CharacterActionCode characterActionCode = CharacterActionCode.fromString(VALID_CHARACTER_ACTION_CODE_STRING);

        assertEquals(VALID_CHARACTER_ACTION_CODE, characterActionCode);
    }

    @Test
    void givenAnInvalidActionCode_whenFromString_thenThrowException() {
        assertThrows(InvalidActionCodeException.class,
                () -> CharacterActionCode.fromString(INVALID_CHARACTER_ACTION_CODE_STRING));
    }
}