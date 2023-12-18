package ca.ulaval.glo4002.game.infrastructure.persistence;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.*;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CharactersInMemoryTest {
    private static final String NAME = "name";
    private static final Type CHARACTER_TYPE = Type.CHINCHILLA;
    private static final int SALARY = 250;

    private CharactersInMemory charactersInMemory;
    private Character character;
    private CharacterFactory characterFactory;

    @BeforeEach
    void initCharactersInMemory() {
        charactersInMemory = new CharactersInMemory();
    }

    @BeforeEach
    void initCharacter() {
        characterFactory = new CharacterFactory();
        character = characterFactory.create(NAME, CHARACTER_TYPE, SALARY);
    }

    @AfterEach
    void resetCharactersInMemory() {
        charactersInMemory.deleteAll();
    }

    @Test
    void givenACharacterInMemory_whenFindByName_thenReturnCharacter() {
        charactersInMemory.create(character);

        Character characterFound = charactersInMemory.findByName(NAME);

        assertEquals(characterFound, character);
    }

    @Test
    void givenNoCharacterInMemory_whenFindByName_thenReturnEmptyOptional() {
        assertThrows(NotFoundCharacterException.class, () -> charactersInMemory.findByName(NAME));
    }

    @Test
    void givenACharacterInMemory_whenDeleteAll_thenCharactersIsEmpty() {
        charactersInMemory.create(character);

        charactersInMemory.deleteAll();

        assertTrue(charactersInMemory.isEmpty());
    }

    @Test
    void givenACharacterInMemory_whenGetAll_thenReturnCharacter() {
        List<Character> charactersExpected = List.of(character);
        charactersInMemory.create(character);

        List<Character> foundCharacters = charactersInMemory.getAll();

        assertEquals(charactersExpected, foundCharacters);
    }

    @Test
    void givenACharacterInMemoryAndUpdatedCharacter_whenSaveAll_thenReplaceCharacter() {
        charactersInMemory.create(character);
        Character updatedCharacter = characterFactory.create(NAME, CHARACTER_TYPE, SALARY + 1);
        Characters updatedCharacters = new Characters(List.of(updatedCharacter));

        charactersInMemory.saveAll(updatedCharacters);

        Character foundCharacter = charactersInMemory.findByName(NAME);
        assertEquals(updatedCharacter, foundCharacter);
    }

    @Test
    void givenACharacterInMemory_whenUpdateHamstersStatus_thenCharacterStatusReplaced() {
        Hamster hamster = (Hamster) characterFactory.create(NAME, Type.HAMSTER, SALARY);
        charactersInMemory.create(hamster);
        hamster.makeNotAvailable();
        List<Hamster> casting = List.of(hamster);

        charactersInMemory.updateHamsters(casting);

        Hamster hamsterFound = (Hamster) charactersInMemory.findByName(NAME);
        boolean characterStatusInMemory = hamster.canBeCast();
        assertEquals(hamsterFound.canBeCast(), characterStatusInMemory);
    }
}