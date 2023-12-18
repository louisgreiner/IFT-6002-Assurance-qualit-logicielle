package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterTypeException;

public enum Type {
    HAMSTER("hamster"),
    CHINCHILLA("chinchilla"),
    RAT("rat");

    private final String type;

    Type(String type) {
        this.type = type;
    }

    public static Type fromString(String typeString) {
        for (Type type : Type.values()) {
            if (type.type.equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new InvalidCharacterTypeException(typeString);
    }

    @Override
    public String toString() {
        return type;
    }
}
