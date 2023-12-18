package ca.ulaval.glo4002.game.interfaces.rest.action;

import ca.ulaval.glo4002.game.application.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ActionResourceTest {
    private static final String FROM_NAME = "FromName";
    private static final String TO_NAME = "ToName";
    private static final String ACTION_CODE = "RS";

    private GameService gameServiceMock;
    private ActionResource actionResource;
    private ActionDto actionDto;

    @BeforeEach
    void initActionsResource() {
        gameServiceMock = mock(GameService.class);
        actionResource = new ActionResource(gameServiceMock);
    }

    @BeforeEach
    void initActionDto() {
        actionDto = new ActionDto(FROM_NAME, TO_NAME, ACTION_CODE);
    }

    @Test
    void givenValidAction_whenApiCreateAction_thenCallCreateAction() {
        actionResource.createAction(actionDto);

        verify(gameServiceMock).createCharacterAction(any(), any(), any());
    }
}