package ca.ulaval.glo4002.game.domain.actions.user.character;

import ca.ulaval.glo4002.game.domain.actions.user.character.exceptions.InvalidActionCodeException;

public enum CharacterActionCode {
    RS("RS"),
    PO("PO"),
    FR("FR"),
    SC("SC"),
    PL("PL");

    private final String characterActionCode;

    CharacterActionCode(String characterActionCode) {
        this.characterActionCode = characterActionCode;
    }

    public static CharacterActionCode fromString(String characterActionCodeString) {
        for (CharacterActionCode characterActionCode : CharacterActionCode.values()) {
            if (characterActionCode.characterActionCode.equalsIgnoreCase(characterActionCodeString)) {
                return characterActionCode;
            }
        }
        throw new InvalidActionCodeException(characterActionCodeString);
    }

    @Override
    public String toString() {
        return characterActionCode;
    }
}
