package ca.ulaval.glo4002.game.interfaces.rest.character.exceptions;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterSalaryException;
import ca.ulaval.glo4002.game.interfaces.exceptions.ExceptionResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InvalidCharacterSalaryExceptionMapper implements ExceptionMapper<InvalidCharacterSalaryException> {
    private static final String error = "INVALID_SALARY";
    private static final String description = "Salary must be > 0.";
    private final Logger logger = LoggerFactory.getLogger(InvalidCharacterSalaryExceptionMapper.class);

    @Override
    public Response toResponse(InvalidCharacterSalaryException exception) {
        ExceptionResponse response = new ExceptionResponse(error, description);

        logger.error(error + " : " + description);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
