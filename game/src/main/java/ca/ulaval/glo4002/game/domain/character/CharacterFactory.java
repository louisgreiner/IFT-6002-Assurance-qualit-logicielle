package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.BankAccountFactory;
import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterNameEmptyException;
import ca.ulaval.glo4002.game.domain.character.exceptions.InvalidCharacterTypeException;
import ca.ulaval.glo4002.game.domain.hamstagram.HamstagramAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInAccountFactory;
import ca.ulaval.glo4002.game.domain.rattedIn.RattedInStatus;

public class CharacterFactory {
    private static final int DEFAULT_REPUTATION = 75;
    private final BankAccountFactory bankAccountFactory;
    private final HamstagramAccountFactory hamstagramAccountFactory;
    private final RattedInAccountFactory rattedInAccountFactory;

    public CharacterFactory() {
        this.bankAccountFactory = new BankAccountFactory();
        this.hamstagramAccountFactory = new HamstagramAccountFactory();
        this.rattedInAccountFactory = new RattedInAccountFactory();
    }

    public Character create(String name, Type type, int salary) {
        if (name.isEmpty()) {
            throw new InvalidCharacterNameEmptyException();
        }

        switch (type) {
            case HAMSTER -> {
                return new Hamster(name, type, salary, DEFAULT_REPUTATION, bankAccountFactory.create(), hamstagramAccountFactory.create(name));
            }
            case CHINCHILLA -> {
                return new Chinchilla(name, type, salary, DEFAULT_REPUTATION, bankAccountFactory.create(), hamstagramAccountFactory.create(name), rattedInAccountFactory.create(name, RattedInStatus.NA));
            }
            case RAT -> {
                return new Rat(name, type, salary, DEFAULT_REPUTATION, bankAccountFactory.create(), rattedInAccountFactory.create(name, RattedInStatus.OPEN_TO_WORK));
            }
            default -> throw new InvalidCharacterTypeException(type.toString());
        }
    }
}
