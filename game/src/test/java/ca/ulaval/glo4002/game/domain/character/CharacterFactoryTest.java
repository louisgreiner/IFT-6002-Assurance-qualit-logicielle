package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterNameEmptyException;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterFactoryTest {
    private static final String NAME = "TestName";
    private static final int SALARY = 40;
    private static final String INVALID_NAME = "";

    private CharacterFactory characterFactory;

    @BeforeEach
    void initCharacterFactory() {
        characterFactory = new CharacterFactory();
    }

    @Test
    void whenCreateHamster_thenReturnHamster() {
        Type hamsterType = Type.HAMSTER;

        Character hamsterCreated = characterFactory.create(NAME, hamsterType, SALARY);

        assertInstanceOf(Hamster.class, hamsterCreated);
    }

    @Test
    void whenCreateChinchilla_thenReturnChinchilla() {
        Type chinchillaType = Type.CHINCHILLA;

        Character chinchillaCreated = characterFactory.create(NAME, chinchillaType, SALARY);

        assertInstanceOf(Chinchilla.class, chinchillaCreated);
    }

    @Test
    void whenCreateRat_thenReturnRat() {
        Type ratType = Type.RAT;

        Character ratCreated = characterFactory.create(NAME, ratType, SALARY);

        assertInstanceOf(Rat.class, ratCreated);
    }

    @Test
    void whenCreateRat_thenRattedInAccountStatusIsOpenToWork() {
        Rat ratCreated = (Rat) characterFactory.create(NAME, Type.RAT, SALARY);

        RattedInAccount rattedInAccount = ratCreated.getRattedInAccount();

        assertEquals(RattedInStatus.OPEN_TO_WORK, rattedInAccount.getRattedInStatus());
    }

    @Test
    void whenCreateChinchilla_thenRattedInAccountStatusIsNA() {
        Chinchilla chinchillaCreated = (Chinchilla) characterFactory.create(NAME, Type.CHINCHILLA, SALARY);

        RattedInAccount rattedInAccount = chinchillaCreated.getRattedInAccount();

        assertEquals(RattedInStatus.NA, rattedInAccount.getRattedInStatus());
    }

    @Test
    void givenEmptyName_whenCreateCharacter_thenThrowException() {
        assertThrows(InvalidCharacterNameEmptyException.class, () -> characterFactory.create(INVALID_NAME, Type.HAMSTER, SALARY));
    }
}