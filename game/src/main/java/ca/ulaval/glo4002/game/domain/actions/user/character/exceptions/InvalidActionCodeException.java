package ca.ulaval.glo4002.game.domain.actions.user.character.exceptions;

public class InvalidActionCodeException extends RuntimeException {
    public InvalidActionCodeException(String characterActionCodeString) {
        super("Character action code is invalid : " + characterActionCodeString);
    }
}
