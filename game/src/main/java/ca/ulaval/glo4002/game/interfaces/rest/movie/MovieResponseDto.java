package ca.ulaval.glo4002.game.interfaces.rest.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MovieResponseDto(
        @JsonProperty("title") String title,
        @JsonProperty("type") String type,
        @JsonProperty("potentialCasting") List<String> potentialCasting,
        @JsonProperty("casting") List<String> casting,
        @JsonProperty("boxOffice") int boxOffice
) {
}
