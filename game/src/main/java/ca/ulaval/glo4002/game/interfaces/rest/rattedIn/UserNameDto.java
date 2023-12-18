package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserNameDto(
        @JsonProperty("username") String username
) {
}