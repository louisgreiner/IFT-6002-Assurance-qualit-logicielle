package ca.ulaval.glo4002.game.domain.turn;

import ca.ulaval.glo4002.game.domain.actions.Actions;
import ca.ulaval.glo4002.game.domain.actions.GameVisitor;
import ca.ulaval.glo4002.game.domain.actions.user.CreateCharacter;
import ca.ulaval.glo4002.game.domain.actions.user.CreateMovie;
import ca.ulaval.glo4002.game.domain.actions.user.character.Scandal;
import ca.ulaval.glo4002.game.domain.actions.user.character.SpreadGossip;
import ca.ulaval.glo4002.game.domain.character.Characters;
import ca.ulaval.glo4002.game.domain.movie.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TurnTest {
    private static final String FROM_NAME = "FromName";
    private static final String TO_NAME = "ToName";

    private Turn turn;
    private Turn turnWithMockedActions;
    private Actions actionsMock;
    private CreateCharacter createCharacterMock;
    private Scandal scandalMock;
    private SpreadGossip spreadGossipMock;
    private CreateMovie createMovieMock;
    private GameVisitor gameVisitorMock;
    private List<Movie> movies;
    private Characters charactersMock;

    @BeforeEach
    void initTurn() {
        turn = new Turn(new Actions());
        actionsMock = mock(Actions.class);
        turnWithMockedActions = new Turn(actionsMock);
    }

    @BeforeEach
    void initGameVisitor() {
        gameVisitorMock = mock(GameVisitor.class);
    }

    @BeforeEach
    void initActions() {
        createCharacterMock = mock(CreateCharacter.class);
        scandalMock = mock(Scandal.class);
        spreadGossipMock = mock(SpreadGossip.class);
        createMovieMock = mock(CreateMovie.class);
    }

    @AfterEach
    void reset() {
        turn.reset();
    }

    @Test
    void whenNewTurn_thenTurnNumberIsZero() {
        assertEquals(0, turn.getTurnNumber());
    }

    @Test
    void whenNextTurn_thenTurnNumberIsIncremented() {
        int expectedTurnNumber = turn.getTurnNumber() + 1;
        charactersMock = mock(Characters.class);
        this.movies = new ArrayList<>();

        turn.nextTurn(charactersMock, movies);

        assertEquals(expectedTurnNumber, turn.getTurnNumber());
    }

    @Test
    void whenNewTurn_thenActionsIsEmpty() {
        assertTrue(turn.actionsIsEmpty());
    }

    @Test
    void whenAddAction_thenActionsIsNotEmpty() {
        turn.addAction(createCharacterMock);

        assertFalse(turn.actionsIsEmpty());
    }

    @Test
    void givenTurnInProgress_whenReset_thenTurnNumberIsZero() {
        this.movies = new ArrayList<>();
        turn.nextTurn(charactersMock, movies);

        turn.reset();

        assertEquals(0, turn.getTurnNumber());
    }

    @Test
    void givenTurnInProgress_whenReset_thenActionsIsEmpty() {
        turn.nextTurn(charactersMock, movies);

        turn.reset();

        assertTrue(turn.actionsIsEmpty());
    }

    @Test
    void givenNoAction_whenExecuteAllActions_thenAcceptNoAction() {
        turn.executeAllActions(gameVisitorMock);

        verify(createCharacterMock, times(0)).accept(any());
    }

    @Test
    void givenAnAction_whenExecuteAllActions_thenAcceptAction() {
        turn.addAction(createCharacterMock);

        turn.executeAllActions(gameVisitorMock);

        verify(createCharacterMock, times(1)).accept(any());
    }

    @Test
    void givenTwoActionsFromSameCharacter_whenExecuteAllActions_thenOnlyFirstIsExecuted() {
        turn.addAction(scandalMock, FROM_NAME, TO_NAME);
        turn.addAction(spreadGossipMock, FROM_NAME, TO_NAME);

        turn.executeAllActions(gameVisitorMock);

        verify(scandalMock, times(1)).accept(any());
        verify(spreadGossipMock, never()).accept(any());
    }

    @Test
    void givenCanMovieBeAdded_whenAddCreateMovieAction_thenCreateMovieActionIsAdded() {
        turn.addCreateMovieAction(createMovieMock);

        turn.executeAllActions(gameVisitorMock);

        verify(createMovieMock, times(1)).accept(any());
    }

    @Test
    void givenNoCanMovieBeAdded_whenAddCreateMovieAction_thenCreateMovieActionIsNotAdded() {
        turn.addCreateMovieAction(createMovieMock);
        turn.addCreateMovieAction(createMovieMock);

        turn.executeAllActions(gameVisitorMock);

        verify(createMovieMock, times(1)).accept(any());
    }

    @Test
    void givenNoAddedMovieThisTurn_whenAddCreateMovieAction_thenAddAction() {
        turnWithMockedActions.addCreateMovieAction(createMovieMock);

        verify(actionsMock, times(1)).add(any());
    }

    @Test
    void givenAddedMovieThisTurn_whenAddCreateMovieAction_thenDontAddAction() {
        turnWithMockedActions.addCreateMovieAction(createMovieMock);
        turnWithMockedActions.addCreateMovieAction(createMovieMock);

        verify(actionsMock, times(1)).add(any());
    }
}