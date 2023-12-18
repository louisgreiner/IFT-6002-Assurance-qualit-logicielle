package ca.ulaval.glo4002.game.domain.actions.nextTurn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.Chinchilla;
import ca.ulaval.glo4002.game.domain.character.Hamster;

import java.util.List;

public class ChinchillasSendHamstagramRequests implements Action {
    private final int priority;

    public ChinchillasSendHamstagramRequests() {
        this.priority = ActionEnumPriority.CHINCHILLA_SEND_HAMSTAGRAM_REQUESTS.getPriority();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void execute(Characters characters) {
        List<Hamster> hamsters = characters.getListOf(Hamster.class);
        List<Chinchilla> chinchillas = characters.getListOf(Chinchilla.class);
        chinchillas.forEach(chinchilla -> chinchilla.sendHamstagramRequests(hamsters));
    }
}
