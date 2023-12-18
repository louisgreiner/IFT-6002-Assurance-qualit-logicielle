package ca.ulaval.glo4002.game.domain.actions.user;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.ActionEnumPriority;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.character.RattedInUser;

public class RattedInRequest implements Action {
    private final String senderName;
    private final String receiverName;
    private final int priority;

    public RattedInRequest(String senderName, String receiverName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.priority = ActionEnumPriority.RATTEDIN_REQUEST.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void execute(Characters characters) {
        if (characters.getByNameAndFilter(senderName, RattedInUser.class).isEmpty()
                || characters.getByNameAndFilter(receiverName, RattedInUser.class).isEmpty()) {
            return;
        }
        RattedInUser sender = characters.getByNameAndFilter(senderName, RattedInUser.class).get();
        RattedInUser receiver = characters.getByNameAndFilter(receiverName, RattedInUser.class).get();

        if (receiver.acceptRattedInRequest(sender)) {
            sender.addRattedInContact(receiver);
            receiver.addRattedInContact(sender);
        }
    }
}