package ca.ulaval.glo4002.game.interfaces.rest.turn;

import ca.ulaval.glo4002.game.application.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TurnResourceTest {
    private GameService gameServiceMock;
    private TurnResource turnResource;

    @BeforeEach
    void initGameService() {
        gameServiceMock = mock(GameService.class);
        this.turnResource = new TurnResource(gameServiceMock);
    }

    @Test
    void verifyWhenApiNextTurn_thenCallNextTurn() {
        turnResource.nextTurn();

        verify(gameServiceMock).nextTurn();
    }
}