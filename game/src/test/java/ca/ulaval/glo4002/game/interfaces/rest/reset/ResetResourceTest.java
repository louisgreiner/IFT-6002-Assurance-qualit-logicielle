package ca.ulaval.glo4002.game.interfaces.rest.reset;

import ca.ulaval.glo4002.game.application.GameService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ResetResourceTest {
    private GameService gameServiceMock;
    private ResetResource resetResource;

    @BeforeEach
    void initGameService() {
        gameServiceMock = mock(GameService.class);
        this.resetResource = new ResetResource(gameServiceMock);
    }

    @Test
    void verifyWhenApiReset_thenCallReset() {
        resetResource.reset();

        verify(gameServiceMock).reset();
    }

    @Test
    void verifyWhenApiReset_thenReturnOKResponse() {
        Response response = resetResource.reset();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
