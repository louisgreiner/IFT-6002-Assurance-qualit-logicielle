package ca.ulaval.glo4002.game.interfaces.rest.rattedIn;

import ca.ulaval.glo4002.game.domain.character.RattedInUser;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccount;

import java.util.List;

public class RattedInAccountMapper {
    public RattedInAccountResponseDto toDto(RattedInAccount rattedInAccount) {
        String username = rattedInAccount.getOwnerName();
        String status = rattedInAccount.getRattedInStatus().toString();
        List<String> contacts = rattedInAccount.getContacts().stream().map(RattedInUser::getName).toList();

        return new RattedInAccountResponseDto(username, status, contacts);
    }
}
