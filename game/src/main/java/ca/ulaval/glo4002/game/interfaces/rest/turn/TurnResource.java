package ca.ulaval.glo4002.game.interfaces.rest.turn;

import ca.ulaval.glo4002.game.application.GameService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/turn")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TurnResource {
    private final Logger logger = LoggerFactory.getLogger(TurnResource.class);
    private final GameService gameService;

    @Inject
    public TurnResource(GameService gameService) {
        this.gameService = gameService;
    }

    @POST
    public TurnResponse nextTurn() {
        int turnNumber = gameService.nextTurn();

        logger.info("Next turn called, turnNumber is now = " + turnNumber);
        return new TurnResponse(turnNumber);
    }
}
