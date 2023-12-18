package ca.ulaval.glo4002.game.interfaces.rest.lawsuits;


import ca.ulaval.glo4002.game.application.GameService;
import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Path("/lawsuits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LawsuitsResource {
    private final Logger logger = LoggerFactory.getLogger(LawsuitsResource.class);
    private final GameService gameService;
    private final LawsuitsMapper lawsuitsMapper;

    @Inject
    public LawsuitsResource(GameService gameService, LawsuitsMapper lawsuitsMapper) {
        this.gameService = gameService;
        this.lawsuitsMapper = lawsuitsMapper;
    }

    @GET
    public List<LawsuitsDto> getLawsuits() {
        List<Lawsuit> lawsuits = gameService.getLawsuits();
        logger.info("Get lawsuits : " + lawsuits.stream().map(Lawsuit::getCharacterName).collect(Collectors.joining("; ")));
        return lawsuits.stream().map(lawsuitsMapper::toDto).toList();
    }
}
