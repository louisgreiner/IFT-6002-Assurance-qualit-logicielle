package ca.ulaval.glo4002.game.domain.character.exceptions;

public class NotFoundCharacterException extends RuntimeException {
    public NotFoundCharacterException(String name) {
        super("Character not found : " + name);
    }
}
