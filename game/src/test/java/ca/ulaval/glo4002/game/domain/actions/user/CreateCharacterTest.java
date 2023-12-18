package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateCharacterTest {
    private static final String NAME = "TestName";
    private static final Type TYPE = Type.CHINCHILLA;
    private static final int SALARY = 1000;

    private CreateCharacter createCharacter;
    private Character character;

    @BeforeEach
    void initCharacter() {
        CharacterFactory characterFactory = new CharacterFactory();
        character = characterFactory.create(NAME, TYPE, SALARY);
    }

    @BeforeEach
    void initCreateCharacter() {
        createCharacter = new CreateCharacter(character);
    }

    @Test
    void whenExecute_thenCreateCharacter() {
        Characters characters = new Characters();
        createCharacter.execute(characters);
        assertTrue(characters.contains(character));
    }

    @Test
    void whenAccept_thenCallVisit() {
        GameVisitor gameVisitorMock = mock(GameVisitor.class);

        createCharacter.accept(gameVisitorMock);

        verify(gameVisitorMock).visit(createCharacter);
    }
}