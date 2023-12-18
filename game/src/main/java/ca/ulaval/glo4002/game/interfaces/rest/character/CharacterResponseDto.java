package ca.ulaval.glo4002.game.interfaces.rest.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterResponseDto(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("reputationScore") int reputationScore,
        @JsonProperty("bankBalance") float bankBalance,
        @JsonProperty("nbLawsuits") int nbLawsuits
) {
}