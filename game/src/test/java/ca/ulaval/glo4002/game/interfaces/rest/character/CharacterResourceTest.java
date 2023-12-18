package ca.ulaval.glo4002.game.interfaces.rest.character;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Type;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CharacterResourceTest {
    private static final String NAME = "TestName";
    private static final String CHARACTER_TYPE_STRING = "rat";
    private static final Type CHARACTER_TYPE = Type.RAT;
    private static final int SALARY = 100;

    private GameService gameServiceMock;
    private CharacterResource characterResource;
    private CharacterFactory characterFactory;
    private BaseCharacterDto baseCharacterDto;

    @BeforeEach
    void initCharacters() {
        characterFactory = new CharacterFactory();
        baseCharacterDto = mock(BaseCharacterDto.class);
        when(baseCharacterDto.name()).thenReturn(NAME);
        when(baseCharacterDto.type()).thenReturn(CHARACTER_TYPE_STRING);
        when(baseCharacterDto.salary()).thenReturn(SALARY);
    }

    @BeforeEach
    void initGameService() {
        gameServiceMock = mock(GameService.class);
        CharacterMapper characterMapperMock = mock(CharacterMapper.class);
        characterResource = new CharacterResource(gameServiceMock, characterMapperMock);
    }

    @Test
    void whenApiCreateCharacter_thenCallCreateCharacter() {
        characterResource.createCharacter(baseCharacterDto);

        verify(gameServiceMock).createCharacter(any(), any(), anyInt());
    }

    @Test
    void whenApiCreateCharacter_thenReturnOKResponse() {
        Response response = characterResource.createCharacter(baseCharacterDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenApiGetCharacter_thenCallGetCharacter() {
        Character character = characterFactory.create(NAME, CHARACTER_TYPE, SALARY);
        when(gameServiceMock.getCharacter(NAME)).thenReturn(character);

        characterResource.getCharacter(NAME);

        verify(gameServiceMock).getCharacter(NAME);
    }
}