package ca.ulaval.glo4002.game.interfaces.rest.movie.exceptions;

import ca.ulaval.glo4002.game.domain.movie.exceptions.InvalidMovieTypeException;
import ca.ulaval.glo4002.game.interfaces.exceptions.ExceptionResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InvalidMovieTypeExceptionMapper implements ExceptionMapper<InvalidMovieTypeException> {
    private static final String error = "INVALID_MOVIE_TYPE";
    private static final String description = "The movie type is invalid.";
    private final Logger logger = LoggerFactory.getLogger(InvalidMovieTypeExceptionMapper.class);

    @Override
    public Response toResponse(InvalidMovieTypeException exception) {
        ExceptionResponse response = new ExceptionResponse(error, description);

        logger.error(error + " : " + description);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
