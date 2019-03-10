import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This strategy uses FIFO queue as a queue data structure. It also terminates
 * tasks some const peroid of time.
 */
public class StrategyRoundRobin extends StrategyQueueBased {

    private static final String name = "RR";
    private int q;
    // Must be < q. This is a time spend over current task in this 'round'.
    private int currentTaskSpentTime;

    public StrategyRoundRobin(int q) {
        this.q = q;
        currentTaskSpentTime = 0;

        processQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public String toString() {
        return name + "-" + Integer.toString(q);
    }

    @Override
    protected void setCurrentTask(int currentTime) {
        super.setCurrentTask(currentTime);
        currentTaskSpentTime = 0;
    }

    @Override
    protected void simulateOneSecond(int currentTime) {
        super.simulateOneSecond(currentTime);

        if (currentTask != null) {
            currentTaskSpentTime++;

            if (currentTaskSpentTime >= q) {
                assert currentTaskSpentTime == q;

                addNewTask(currentTask, currentTime + 1);
                currentTask = null;
                if (!processQueue.isEmpty())
                    setCurrentTask(currentTime + 1);
            }
        }
    }
}
