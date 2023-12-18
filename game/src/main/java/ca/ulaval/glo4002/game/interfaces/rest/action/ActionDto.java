package ca.ulaval.glo4002.game.interfaces.rest.action;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ActionDto(
        @JsonProperty("from") String from,
        @JsonProperty("to") String to,
        @JsonProperty("actionCode") String actionCode
) {
}
