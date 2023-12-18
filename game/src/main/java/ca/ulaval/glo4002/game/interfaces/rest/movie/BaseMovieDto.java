package ca.ulaval.glo4002.game.interfaces.rest.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BaseMovieDto(
        @JsonProperty("title") String title,
        @JsonProperty("type") String type
) {
}