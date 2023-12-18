package ca.ulaval.glo4002.game.interfaces.rest.action.exceptions;

import ca.ulaval.glo4002.game.domain.actions.user.character.exceptions.InvalidActionCodeException;
import ca.ulaval.glo4002.game.interfaces.exceptions.ExceptionResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InvalidActionCodeExceptionMapper implements ExceptionMapper<InvalidActionCodeException> {
    private static final String error = "INVALID_ACTION_CODE";
    private static final String description = "The action code is invalid.";
    private final Logger logger = LoggerFactory.getLogger(InvalidActionCodeExceptionMapper.class);

    @Override
    public Response toResponse(InvalidActionCodeException exception) {
        ExceptionResponse response = new ExceptionResponse(error, description);

        logger.error(error + " : " + description);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
