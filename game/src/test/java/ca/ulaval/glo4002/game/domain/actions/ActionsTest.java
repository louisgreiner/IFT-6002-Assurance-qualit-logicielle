package ca.ulaval.glo4002.game.domain.actions;

import ca.ulaval.glo4002.game.domain.character.Characters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ActionsTest {
    private static final int TURN_NUMBER = 1;
    private static final int INFERIOR_PRIORITY = 1;
    private static final int SUPERIOR_PRIORITY = 2;
    private static final int PRIORITY = 3;

    private Actions actions;
    private Action actionMock;
    private GameVisitor gameVisitor;

    @BeforeEach
    void initActions() {
        actions = new Actions();
        actionMock = mock(Action.class);
        actions.add(actionMock);
    }

    @BeforeEach
    void initGameVisitor() {
        gameVisitor = new GameVisitor(new Characters(), new ArrayList<>(), TURN_NUMBER);
    }

    @Test
    void givenAnAction_whenExecuteAll_thenAcceptAction() {
        actions.executeAll(gameVisitor);

        verify(actionMock).accept(gameVisitor);
    }

    @Test
    void givenAnInferiorPriority_whenQueueNodeCompareTo_thenReturnNegativeNumber() {
        when(actionMock.getPriority()).thenReturn(INFERIOR_PRIORITY);
        Actions.QueueNode queueNode = new Actions.QueueNode(actionMock);
        Action superiorActionMock = mock(Action.class);
        when(superiorActionMock.getPriority()).thenReturn(SUPERIOR_PRIORITY);
        Actions.QueueNode superiorQueueNode = new Actions.QueueNode(superiorActionMock);

        int comparison = queueNode.compareTo(superiorQueueNode);

        assertTrue(comparison < 0);
    }

    @Test
    void givenASuperiorPriority_whenQueueNodeCompareTo_thenReturnPositiveNumber() {
        when(actionMock.getPriority()).thenReturn(SUPERIOR_PRIORITY);
        Actions.QueueNode queueNode = new Actions.QueueNode(actionMock);
        Action inferiorActionMock = mock(Action.class);
        when(inferiorActionMock.getPriority()).thenReturn(INFERIOR_PRIORITY);
        Actions.QueueNode superiorQueueNode = new Actions.QueueNode(inferiorActionMock);

        int comparison = queueNode.compareTo(superiorQueueNode);

        assertTrue(comparison > 0);
    }

    @Test
    void givenSamePriorityAndGreaterSequence_whenQueueNodeCompareTo_thenReturnNegativeNumber() {
        when(actionMock.getPriority()).thenReturn(PRIORITY);
        Actions.QueueNode queueNode = new Actions.QueueNode(actionMock);
        Actions.QueueNode greaterSequenceQueueNode = new Actions.QueueNode(actionMock);

        int comparison = queueNode.compareTo(greaterSequenceQueueNode);

        assertTrue(comparison < 0);
    }

    @Test
    void givenSamePriorityAndSmallerSequence_whenQueueNodeCompareTo_thenReturnPositiveNumber() {
        when(actionMock.getPriority()).thenReturn(PRIORITY);
        Actions.QueueNode smallerSequenceQueueNode = new Actions.QueueNode(actionMock);
        Actions.QueueNode queueNode = new Actions.QueueNode(actionMock);

        int comparison = queueNode.compareTo(smallerSequenceQueueNode);

        assertTrue(comparison > 0);
    }
}