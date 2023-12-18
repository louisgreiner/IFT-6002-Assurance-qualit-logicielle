package ca.ulaval.glo4002.game.interfaces.exceptions.unknown;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UnknownErrorExceptionResponse(
        @JsonProperty("message") String message
) {
}