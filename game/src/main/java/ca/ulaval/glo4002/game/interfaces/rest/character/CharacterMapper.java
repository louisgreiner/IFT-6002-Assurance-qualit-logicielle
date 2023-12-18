package ca.ulaval.glo4002.game.interfaces.rest.character;

import ca.ulaval.glo4002.game.domain.character.Character;

public class CharacterMapper {
    public CharacterResponseDto toDto(Character character) {
        String name = character.getName();
        String type = character.getType().toString();
        int reputationScore = character.getReputation();
        float bankBalance = character.getBankBalance();
        int nbLawsuits = character.getNbLawsuits();

        return new CharacterResponseDto(name, type, reputationScore, bankBalance, nbLawsuits);
    }
}

