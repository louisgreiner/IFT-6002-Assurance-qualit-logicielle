package ca.ulaval.glo4002.game.interfaces.rest.character;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CharacterMapperTest {
    private static final String CHARACTER_NAME = "name";
    private static final int CHARACTER_SALARY = 100;
    private static final Type CHARACTER_TYPE = Type.HAMSTER;

    private CharacterMapper characterMapper;
    private Character characterMock;
    private Character character;

    @BeforeEach
    void initCharacterMapper() {
        characterMapper = new CharacterMapper();
    }

    @BeforeEach
    void initCharacterMock() {
        characterMock = mock(Character.class);
    }

    @BeforeEach
    void initCharacter() {
        CharacterFactory characterFactory = new CharacterFactory();
        character = characterFactory.create(CHARACTER_NAME, CHARACTER_TYPE, CHARACTER_SALARY);
    }

    @Test
    void givenACharacter_whenMappingToDto_thenCallsCharacterMethods() {
        when(characterMock.getType()).thenReturn(CHARACTER_TYPE);

        characterMapper.toDto(characterMock);

        verify(characterMock).getName();
        verify(characterMock).getType();
        verify(characterMock).getReputation();
        verify(characterMock).getBankBalance();
        verify(characterMock).getNbLawsuits();
    }

    @Test
    void givenACharacter_whenMappingToDto_thenDtoHasSameAttributes() {
        CharacterResponseDto characterResponseDto = characterMapper.toDto(character);

        assertEquals(character.getName(), characterResponseDto.name());
        assertEquals(character.getType().toString(), characterResponseDto.type());
        assertEquals(character.getReputation(), characterResponseDto.reputationScore());
        assertEquals(character.getBankBalance(), characterResponseDto.bankBalance());
        assertEquals(character.getLawsuits().size(), characterResponseDto.nbLawsuits());
    }
}