package ca.ulaval.glo4002.game.interfaces.rest.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BaseCharacterDto(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("salary") int salary
) {
}