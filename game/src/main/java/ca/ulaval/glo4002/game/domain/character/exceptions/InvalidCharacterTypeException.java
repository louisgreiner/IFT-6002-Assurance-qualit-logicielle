package ca.ulaval.glo4002.game.domain.character.exceptions;

public class InvalidCharacterTypeException extends RuntimeException {
    public InvalidCharacterTypeException(String type) {
        super("Type is invalid : " + type);
    }
}
