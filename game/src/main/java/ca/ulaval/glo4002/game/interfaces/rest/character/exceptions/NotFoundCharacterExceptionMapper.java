package ca.ulaval.glo4002.game.interfaces.rest.character.exceptions;

import ca.ulaval.glo4002.game.domain.character.exceptions.NotFoundCharacterException;
import ca.ulaval.glo4002.game.interfaces.exceptions.ExceptionResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class NotFoundCharacterExceptionMapper implements ExceptionMapper<NotFoundCharacterException> {
    private static final String error = "CHARACTER_NOT_FOUND";
    private static final String description = "Character not found.";
    private final Logger logger = LoggerFactory.getLogger(NotFoundCharacterExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundCharacterException exception) {
        ExceptionResponse response = new ExceptionResponse(error, description);

        logger.error(error + " : " + description);

        return Response.status(Response.Status.NOT_FOUND)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
