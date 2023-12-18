package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RattedInAccountResponseDto(
        @JsonProperty("username") String username,
        @JsonProperty("status") String status,
        @JsonProperty("contacts") List<String> contacts
) {
}
