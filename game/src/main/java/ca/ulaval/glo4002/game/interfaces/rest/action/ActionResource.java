package ca.ulaval.glo4002.game.interfaces.rest.action;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.actions.user.character.CharacterActionCode;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/actions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActionResource {
    private final Logger logger = LoggerFactory.getLogger(ActionResource.class);
    private final GameService gameService;

    @Inject
    public ActionResource(GameService gameService) {
        this.gameService = gameService;
    }

    @POST
    public Response createAction(ActionDto actionDto) {
        gameService.createCharacterAction(actionDto.from(), actionDto.to(), CharacterActionCode.fromString(actionDto.actionCode()));
        logger.info("Post Action : " + actionDto.actionCode());
        return Response.status(Response.Status.OK).build();
    }
}
