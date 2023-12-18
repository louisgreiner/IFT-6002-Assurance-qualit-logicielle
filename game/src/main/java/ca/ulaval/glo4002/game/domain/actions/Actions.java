package ca.ulaval.glo4002.game.domain.actions;

import java.util.PriorityQueue;

public class Actions {
    private static long sequenceNumber = 0;
    private final PriorityQueue<QueueNode> actionsQueue;

    public Actions() {
        this.actionsQueue = new PriorityQueue<>();
    }

    public void clear() {
        actionsQueue.clear();
    }

    public boolean isEmpty() {
        return actionsQueue.isEmpty();
    }

    public void add(Action action) {
        actionsQueue.add(new QueueNode(action));
    }

    public void executeAll(Visitor visitor) {
        while (!actionsQueue.isEmpty()) {
            Action action = actionsQueue.poll().action;
            action.accept(visitor);
        }
    }

    protected static class QueueNode implements Comparable<QueueNode> {
        Action action;
        int priority;
        long sequence;

        public QueueNode(Action action) {
            this.action = action;
            this.priority = action.getPriority();
            this.sequence = sequenceNumber++;
        }

        @Override
        public int compareTo(QueueNode other) {
            int priorityComparison = Integer.compare(this.priority, other.priority);
            if (priorityComparison == 0) {
                return Long.compare(this.sequence, other.sequence);
            }
            return priorityComparison;
        }
    }
}
