package ca.ulaval.glo4002.game.domain.lawsuits;

import java.util.Optional;

public final class Lawsuit {
    private final int turnNumber;
    private final String characterName;
    private final String actionCode;
    private Optional<String> lawyerName;

    public Lawsuit(int turnNumber, String characterName, String actionCode, Optional<String> lawyerName) {
        this.turnNumber = turnNumber;
        this.characterName = characterName;
        this.actionCode = actionCode;
        this.lawyerName = lawyerName;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public String getCharacterName() {
        return characterName;
    }

    public String getActionCode() {
        return actionCode;
    }

    public Optional<String> getLawyerName() {
        return lawyerName;
    }

    public void updateLawyer(Optional<String> lawyerName) {
        this.lawyerName = lawyerName;
    }

}