package ca.ulaval.glo4002.game.domain.character.exceptions;

public class InvalidCharacterNameEmptyException extends RuntimeException {
    public InvalidCharacterNameEmptyException() {
        super("Character name cannot be empty");
    }
}
