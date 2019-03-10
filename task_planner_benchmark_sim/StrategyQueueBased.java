import java.util.AbstractQueue;

/**
 * Strategy that does one task at the time, and holds upcomming task in (any)
 * queue data structure (AbstractQueue is used to achieve it).
 */
public abstract class StrategyQueueBased extends Strategy {

    // Currently done task.
    protected Task currentTask;
    // List of tasks of the current simulation.
    protected AbstractQueue<Task> processQueue;

    protected void setCurrentTask(int currentTime) {
        Task nextTask = processQueue.poll();
        assert nextTask != null;

        nextTask.setTotalWaitingTime(nextTask.getTotalWaitingTime() +
                (currentTime - nextTask.getLastQueueTime()));

        currentTask = nextTask;
    }

    @Override
    protected void addNewTask(Task task, int currentTime) {
        task.setLastQueueTime(currentTime);
        processQueue.add(task);
    }

    @Override
    protected void simulateOneSecond(int currentTime) {

        // If current task is null, try to select one from the queue.
        if (currentTask == null && !processQueue.isEmpty()) {
            setCurrentTask(currentTime);
            assert currentTask != null;
        }

        if (currentTask != null) {
            currentTask.setRemainingTime(currentTask.getRemainingTime() - 1);
            if (currentTask.getRemainingTime() <= 0) {
                assert currentTask.getRemainingTime() == 0.0f;

                // NOTE: since simulateOneSecond is done before incrementing
                // time variable we must add 1 here.
                currentTask.setFinishTime(currentTime + 1);
                currentTask = null;
            }
        }
    }

    @Override
    protected boolean hasFinished() {
        return super.hasFinished() && currentTask == null &&
                processQueue.isEmpty();
    }

}
