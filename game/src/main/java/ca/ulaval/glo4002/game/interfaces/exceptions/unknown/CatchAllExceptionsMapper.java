package ca.ulaval.glo4002.game.interfaces.exceptions.unknown;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class CatchAllExceptionsMapper implements ExceptionMapper<Exception> {
    private final Logger logger = LoggerFactory.getLogger(CatchAllExceptionsMapper.class);

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Exception exception) {
        var path = request.getPathInfo();
        String errorMessage = exception.getMessage() + " (invalid path = " + path + ")";
        UnknownErrorExceptionResponse response = new UnknownErrorExceptionResponse(errorMessage);

        logger.error("Unhandled exception in " + path, exception);

        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(response)
                .build();
    }
}