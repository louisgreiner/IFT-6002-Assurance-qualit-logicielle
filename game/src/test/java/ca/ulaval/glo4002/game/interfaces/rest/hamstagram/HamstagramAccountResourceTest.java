package ca.ulaval.glo4002.game.interfaces.rest.hamstagram;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Hamster;
import ca.ulaval.glo4002.game.domain.character.Type;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class HamstagramAccountResourceTest {
    private static final String OWNER_NAME = "TestOwnerName";
    private static final Type CHARACTER_TYPE_HAMSTER = Type.HAMSTER;
    private static final int SALARY = 1000;

    private GameService gameServiceMock;
    private HamstagramAccountResource hamstagramAccountResource;
    private CharacterFactory characterFactory;

    @BeforeEach
    void initCharacterFactory() {
        characterFactory = new CharacterFactory();
    }

    @BeforeEach
    void initGameServiceMock() {
        gameServiceMock = mock(GameService.class);
        HamstagramAccountMapper hamstagramAccountMapperMock = mock(HamstagramAccountMapper.class);
        this.hamstagramAccountResource = new HamstagramAccountResource(gameServiceMock, hamstagramAccountMapperMock);
    }

    @Test
    void whenApiGetCharacter_thenCallGetCharacterAndGetHamstagramAccount() {
        Hamster character = (Hamster) characterFactory.create(OWNER_NAME, CHARACTER_TYPE_HAMSTER, SALARY);
        when(gameServiceMock.getCharacter(OWNER_NAME)).thenReturn(character);
        when(gameServiceMock.getHamstagramAccount(OWNER_NAME)).thenReturn(Optional.ofNullable(character.getHamstagramAccount()));

        hamstagramAccountResource.getHamstagramAccount(OWNER_NAME);

        verify(gameServiceMock).getCharacter(OWNER_NAME);
        verify(gameServiceMock).getHamstagramAccount(OWNER_NAME);
    }

    @Test
    void givenNoHamstagramAccount_whenApiGetHamstagramAccount_thenThrowException() {
        assertThrows(NotFoundCharacterException.class, () -> hamstagramAccountResource.getHamstagramAccount(OWNER_NAME));
    }
}