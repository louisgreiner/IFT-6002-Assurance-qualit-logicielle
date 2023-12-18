package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;
import ca.ulaval.glo4002.game.interfaces.rest.hamstagram.HamstagramAccountResource;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Path("/rattedin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RattedInAccountResource {
    private final Logger logger = LoggerFactory.getLogger(HamstagramAccountResource.class);
    private final GameService gameService;
    private final RattedInAccountMapper rattedInAccountMapper;

    @Inject
    public RattedInAccountResource(GameService gameService, RattedInAccountMapper rattedInAccountMapper) {
        this.gameService = gameService;
        this.rattedInAccountMapper = rattedInAccountMapper;
    }

    @Path("/{username}/request")
    @POST
    public Response requestRattedInContact(@PathParam("username") String receiverName, UserNameDto senderNameDto) {
        gameService.createNewRattedInContactRequest(senderNameDto.username(), receiverName);
        logger.info("Post RattedInAccount contact Request from " + senderNameDto.username() + " to " + receiverName);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/{username}/")
    @GET
    public RattedInAccountResponseDto getRattedInAccount(@PathParam("username") String userName) {
        Optional<RattedInAccount> rattedInAccount = gameService.getRattedInAccount(userName);
        if (rattedInAccount.isEmpty()) {
            throw new NotFoundCharacterException(userName);
        }
        logger.info("Get RattedInAccount : " + userName);
        return rattedInAccountMapper.toDto(rattedInAccount.get());
    }
}
