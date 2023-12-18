package ca.ulaval.glo4002.game.domain.character;

import ca.ulaval.glo4002.game.domain.bank.Payable;

import java.util.List;
import java.util.Optional;

public interface HamstagramManager extends Payable {
    void sendHamstagramRequests(List<Hamster> hamsters);

    String getName();

    void representNewHamster(Hamster hamster);

    int getReputation();

    Optional<Rat> findLawyerForHamsters(Characters characters);

    void removeCelebrityRepresented(Hamster hamster);
}