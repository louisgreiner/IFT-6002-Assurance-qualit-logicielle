package ca.ulaval.glo4002.game.domain.turn;

public interface TurnRepo {
    Turn get();

    void update(Turn turn);
}
