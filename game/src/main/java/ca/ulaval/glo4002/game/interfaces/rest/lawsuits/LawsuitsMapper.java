package ca.ulaval.glo4002.game.interfaces.rest.lawsuits;

import ca.ulaval.glo4002.game.domain.lawsuits.Lawsuit;

import java.util.Optional;

public class LawsuitsMapper {
    public LawsuitsDto toDto(Lawsuit lawsuit) {
        int turnNumber = lawsuit.getTurnNumber();
        String characterName = lawsuit.getCharacterName();
        String actionCode = lawsuit.getActionCode();
        Optional<String> lawyerName = lawsuit.getLawyerName();
        String lawyerNameString = lawyerName.orElse(null);

        return new LawsuitsDto(turnNumber, characterName, actionCode, lawyerNameString);
    }
}
