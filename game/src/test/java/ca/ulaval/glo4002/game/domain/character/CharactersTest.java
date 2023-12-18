package ca.ulaval.glo4002.game.domain.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharactersTest {
    private static final int DEFAULT_SALARY = 100;
    private static final String DEFAULT_NAME = "name";
    private static final String UNKNOWN_NAME = "unknownName";
    private static final String DEFAULT_HAMSTER_NAME = "Hamster";
    private static final String DEFAULT_CHINCHILLA_NAME = "Chinchilla";

    private Characters characters;
    private Character character;
    private Character hamster;
    private Character chinchilla;

    @BeforeEach
    void initCharacters() {
        characters = new Characters();
    }

    @BeforeEach
    void initCharactersEntities() {
        CharacterFactory characterFactory = new CharacterFactory();
        character = characterFactory.create(DEFAULT_NAME, Type.RAT, DEFAULT_SALARY);
        hamster = characterFactory.create(DEFAULT_HAMSTER_NAME, Type.HAMSTER, DEFAULT_SALARY);
        chinchilla = characterFactory.create(DEFAULT_CHINCHILLA_NAME, Type.CHINCHILLA, DEFAULT_SALARY);
    }

    @Test
    void givenAName_whenGetByName_thenOptionalIsNotEmpty() {
        characters.add(character);

        Optional<Character> characterFound = characters.getByName(DEFAULT_NAME);

        assertEquals(character, characterFound.get());
    }

    @Test
    void givenUnknownName_whenGetByName_thenOptionalIsEmpty() {
        characters.add(character);

        Optional<Character> characterFound = characters.getByName(UNKNOWN_NAME);

        assertTrue(characterFound.isEmpty());
    }

    @Test
    void givenHamsterInCharacters_whenGetHamsters_thenReturnListOfHamstersNotEmpty() {
        characters.add(hamster);

        List<Hamster> hamsters = characters.getListOf(Hamster.class);

        assertEquals(hamster, hamsters.get(0));
    }

    @Test
    void givenNoHamster_whenGetHamsters_thenReturnEmptyHamsterList() {
        List<Hamster> hamsters = characters.getListOf(Hamster.class);

        assertTrue(hamsters.isEmpty());
    }

    @Test
    void givenChinchillaInCharacters_whenGetChinchillas_thenReturnListOfChinchillasNotEmpty() {
        characters.add(chinchilla);

        List<Chinchilla> chinchillas = characters.getListOf(Chinchilla.class);

        assertEquals(chinchilla, chinchillas.get(0));
    }

    @Test
    void givenNoChinchilla_whenGetChinchillas_thenReturnEmptyChinchillaList() {
        List<Chinchilla> chinchillas = characters.getListOf(Chinchilla.class);

        assertTrue(chinchillas.isEmpty());
    }
}