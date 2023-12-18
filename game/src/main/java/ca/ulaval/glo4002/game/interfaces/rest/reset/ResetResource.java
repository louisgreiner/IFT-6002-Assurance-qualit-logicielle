package ca.ulaval.glo4002.game.interfaces.rest.reset;

import ca.ulaval.glo4002.game.application.GameService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/reset")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResetResource {
    private final Logger logger = LoggerFactory.getLogger(ResetResource.class);
    private final GameService gameService;

    @Inject
    public ResetResource(GameService gameService) {
        this.gameService = gameService;
    }

    @POST
    public Response reset() {
        gameService.reset();

        logger.info("Reset called, turn has been reset");
        return Response.status(Response.Status.OK).build();
    }
}
