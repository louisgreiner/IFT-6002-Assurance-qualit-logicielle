package ca.ulaval.glo4002.game.domain.actions;

public interface Action {
    void accept(Visitor visitor);

    int getPriority();
}
