package ca.ulaval.glo4002.game.interfaces.rest.turn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TurnResponse {
    public int turnNumber;

    @JsonCreator
    public TurnResponse(@JsonProperty(value = "turnNumber", required = true) Integer turnNumber) {
        this.turnNumber = turnNumber;
    }
}
