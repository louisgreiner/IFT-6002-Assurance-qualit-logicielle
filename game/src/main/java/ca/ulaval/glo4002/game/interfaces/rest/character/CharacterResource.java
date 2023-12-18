package ca.ulaval.glo4002.game.interfaces.rest.character;

import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.Type;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/characters")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CharacterResource {
    private final Logger logger = LoggerFactory.getLogger(CharacterResource.class);
    private final GameService gameService;
    private final CharacterMapper characterMapper;

    @Inject
    public CharacterResource(GameService gameService, CharacterMapper characterMapper) {
        this.gameService = gameService;
        this.characterMapper = characterMapper;
    }

    @POST
    public Response createCharacter(BaseCharacterDto baseCharacterDto) {
        gameService.createCharacter(baseCharacterDto.name(), Type.fromString(baseCharacterDto.type()), baseCharacterDto.salary());
        logger.info("CreateCharacter call added to actions stack (name = " + baseCharacterDto.name() + ", type = " + baseCharacterDto.type() + ", salary = " + baseCharacterDto.salary() + ")");
        return Response.status(Response.Status.OK).build();
    }

    @Path("/{name}")
    @GET
    public CharacterResponseDto getCharacter(@PathParam("name") String name) {
        Character character = gameService.getCharacter(name);
        logger.info("Get Character : " + name);
        return characterMapper.toDto(character);
    }
}
