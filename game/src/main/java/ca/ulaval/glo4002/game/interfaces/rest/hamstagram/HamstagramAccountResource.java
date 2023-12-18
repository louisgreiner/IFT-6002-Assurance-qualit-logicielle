package ca.ulaval.glo4002.game.interfaces.rest.hamstagram;


import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Path("/hamstagram")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HamstagramAccountResource {
    private final Logger logger = LoggerFactory.getLogger(HamstagramAccountResource.class);
    private final GameService gameService;
    private final HamstagramAccountMapper hamstagramAccountMapper;

    @Inject
    public HamstagramAccountResource(GameService gameService, HamstagramAccountMapper hamstagramAccountMapper) {
        this.gameService = gameService;
        this.hamstagramAccountMapper = hamstagramAccountMapper;
    }

    @Path("/{userid}")
    @GET
    public HamstagramAccountResponseDto getHamstagramAccount(@PathParam("userid") String ownerName) {
        Optional<HamstagramAccount> hamstagramAccount = gameService.getHamstagramAccount(ownerName);
        if (hamstagramAccount.isEmpty()) {
            throw new NotFoundCharacterException(ownerName);
        }
        Character character = gameService.getCharacter(ownerName);
        logger.info("Get HamstagramAccount : " + ownerName);
        return hamstagramAccountMapper.toDto(hamstagramAccount.get(), character);
    }
}
