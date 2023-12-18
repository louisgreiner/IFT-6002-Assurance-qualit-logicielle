package ca.ulaval.glo4002.game.interfaces.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExceptionResponse(
        @JsonProperty("error") String error,
        @JsonProperty("description") String description
) {
}