/**
 * Strategy that selects next tasks based on time, it requires.
 */
public class StrategySJF extends StrategyPriorityBased {

    private static final String name = "SJF";

    @Override
    public String toString() {
        return name;
    }

    // Should use x.getTotalTime instead, but it is the same, since in this
    // strategy we do not remove already started tasks from the queue.
    @Override
    protected int compare(Task x, Task y) {
        int processorDiff = (int) Math.signum(x.getRemainingTime() -
                y.getRemainingTime());

        return processorDiff != 0 ? processorDiff : x.getId() - y.getId();
    }

}
