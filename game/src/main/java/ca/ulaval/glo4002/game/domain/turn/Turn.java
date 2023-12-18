package ca.ulaval.glo4002.game.domain.turn;

import ca.ulaval.glo4002.game.domain.actions.Action;
import ca.ulaval.glo4002.game.domain.actions.Actions;
import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.actions.Visitor;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final Actions actions;
    private final List<String> fromNames;
    private final List<String> toNames;
    private boolean canMovieBeAdded = true;
    private int turnNumber = 0;
    private GameVisitor gameVisitor;

    public Turn(Actions actions) {
        this.actions = actions;
        this.fromNames = new ArrayList<>();
        this.toNames = new ArrayList<>();
    }

    public void nextTurn(Characters characters, List<Movie> movies) {
        turnNumber++;
        gameVisitor = new GameVisitor(characters, movies, turnNumber);
        canMovieBeAdded = true;
        fromNames.clear();
        toNames.clear();
        executeAllActions(gameVisitor);
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public GameVisitor getGameVisitor() {
        return gameVisitor;
    }

    public void reset() {
        turnNumber = 0;
        actions.clear();
        fromNames.clear();
        toNames.clear();
        canMovieBeAdded = true;
    }

    public boolean actionsIsEmpty() {
        return actions.isEmpty();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void addAction(Action action, String from, String to) {
        if (fromNames.contains(from) || toNames.contains(to)) {
            return;
        }
        fromNames.add(from);
        toNames.add(to);
        addAction(action);
    }

    public void addCreateMovieAction(Action createMovie) {
        if (canMovieBeAdded) {
            addAction(createMovie);
            canMovieBeAdded = false;
        }
    }

    public void executeAllActions(Visitor visitor) {
        actions.executeAll(visitor);
    }
}
