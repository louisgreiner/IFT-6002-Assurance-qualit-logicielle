package ca.ulaval.glo4002.game.interfaces.rest.hamstagram;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HamstagramAccountResponseDto(
        @JsonProperty("username") String username,
        @JsonProperty("nbFollowers") int nbFollowers,
        @JsonProperty("represent") List<String> represent,
        @JsonProperty("representedBy") String representedBy
) {
}
