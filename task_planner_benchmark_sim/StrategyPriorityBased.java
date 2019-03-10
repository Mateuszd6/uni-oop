import java.util.PriorityQueue;

/**
 * Class that selects tasks to proceed based on compare function, which is
 * the only thing that has to be implemented.
 */
public abstract class StrategyPriorityBased extends StrategyQueueBased {

    public StrategyPriorityBased() {
        processQueue = new PriorityQueue<>(16, this::compare);
    }

    protected abstract int compare(Task x, Task y);
}
