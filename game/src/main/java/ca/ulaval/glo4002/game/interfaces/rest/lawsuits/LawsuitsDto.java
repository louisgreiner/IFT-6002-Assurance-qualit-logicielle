package ca.ulaval.glo4002.game.interfaces.rest.lawsuits;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LawsuitsDto(
        @JsonProperty("turnNumber") int turnNumber,
        @JsonProperty("characterName") String characterName,
        @JsonProperty("actionCode") String actionCode,
        @JsonProperty("lawyerName") String lawyerName
) {
}