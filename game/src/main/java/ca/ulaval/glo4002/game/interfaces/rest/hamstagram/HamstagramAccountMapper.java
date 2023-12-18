package ca.ulaval.glo4002.game.interfaces.rest.hamstagram;

import ca.ulaval.glo4002.game.domain.character.Character;
import ca.ulaval.glo4002.game.domain.character.HamstagramCelebrity;
import ca.ulaval.glo4002.game.domain.character.HamstagramManager;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccount;

import java.util.List;

public class HamstagramAccountMapper {
    public HamstagramAccountResponseDto toDto(HamstagramAccount hamstagramAccount, Character manager) {
        String username = hamstagramAccount.getOwnerName();
        int nbFollowers = hamstagramAccount.getFollowersNumber();
        List<String> represent = manager.getCelebrityRepresented().stream().map(HamstagramCelebrity::getName).toList();
        if (manager.getManager().isPresent()) {
            HamstagramManager hamstagramManager = manager.getManager().get();
            String representedBy = hamstagramManager.getName();
            return new HamstagramAccountResponseDto(username, nbFollowers, represent, representedBy);
        } else {
            String representedBy = "";
            return new HamstagramAccountResponseDto(username, nbFollowers, represent, representedBy);
        }
    }
}