package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.CharacterFactory;
import ca.ulaval.glo4002.game.domain.character.Rat;
import ca.ulaval.glo4002.game.domain.character.Type;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RattedInAccountResourceTest {
    private static final String OWNER_NAME = "TestOwnerName";
    private static final Type CHARACTER_RAT_TYPE = Type.RAT;
    private static final int SALARY = 1000;
    private static final String SENDER_NAME = "SenderName";
    private static final String RECEIVER_NAME = "ReceiverName";

    private GameService gameServiceMock;
    private RattedInAccountResource rattedInAccountResource;
    private CharacterFactory characterFactory;
    private UserNameDto userNameDto;

    @BeforeEach
    void initCharacterFactory() {
        characterFactory = new CharacterFactory();
    }

    @BeforeEach
    void initGameServiceMock() {
        gameServiceMock = mock(GameService.class);
        RattedInAccountMapper rattedInAccountMapperMock = mock(RattedInAccountMapper.class);
        this.rattedInAccountResource = new RattedInAccountResource(gameServiceMock, rattedInAccountMapperMock);
    }

    @BeforeEach
    void initUserNameDto() {
        userNameDto = new UserNameDto(SENDER_NAME);
    }

    @Test
    void whenApiPostRequestRattedInContact_thenCallCreateNewRattedInContactRequest() {
        rattedInAccountResource.requestRattedInContact(RECEIVER_NAME, userNameDto);

        verify(gameServiceMock).createNewRattedInContactRequest(any(), eq(RECEIVER_NAME));
    }

    @Test
    void whenApiPostRequestRattedInContact_thenReturnOKResponse() {
        Response response = rattedInAccountResource.requestRattedInContact(RECEIVER_NAME, userNameDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void whenApiGetGetRattedInAccount_thenCallGetRattedInAccount() {
        Rat character = (Rat) characterFactory.create(OWNER_NAME, CHARACTER_RAT_TYPE, SALARY);
        when(gameServiceMock.getRattedInAccount(OWNER_NAME)).thenReturn(Optional.ofNullable(character.getRattedInAccount()));

        rattedInAccountResource.getRattedInAccount(OWNER_NAME);

        verify(gameServiceMock).getRattedInAccount(OWNER_NAME);
    }

    @Test
    void givenNoRattedInAccount_whenApiGetRattedInAccount_thenThrowException() {
        assertThrows(NotFoundCharacterException.class, () -> rattedInAccountResource.getRattedInAccount(OWNER_NAME));
    }
}