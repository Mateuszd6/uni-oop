import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Abstract strategy class. Implements benchmark function which is done by
 * calling simulateOneSecond and hasFinished function which are the only ones
 * that have to be overloaded in the child classes.
 */
public abstract class Strategy {

    private PriorityQueue<Task> tasksToAdd = new PriorityQueue<>(
            this::taskToAddComp);

    private int taskToAddComp(Task x, Task y) {
        return x.getAppearanceTime() - y.getAppearanceTime() == 0
                ? x.getId() - y.getId()
                : x.getAppearanceTime() - y.getAppearanceTime();
    }

    @Override
    public abstract String toString();

    // By default the task is just added to the end of the queue, but some
    // strategies would like to stop the current task and replace it with the
    // incomming one so it is marked as protected to give this possibility
    // to childs classes.
    protected abstract void addNewTask(Task task, int currentTime);

    // Make a single step of the simulation.
    protected abstract void simulateOneSecond(int currentTime);

    // True if simulation is finished. Will probobly need to be overrriden
    // in the childs classes.
    protected boolean hasFinished() {
        return tasksToAdd.isEmpty();
    }

    // This function returns the benchark data object based on the
    // [runBenchmark] function which must be implemented in the child classes,
    // which just modyfies given tasklist. Result BenchmarkInfo object
    // is constructed based on these changes.
    public final BenchmarkInfo benchmark(Task[] originalTaskList) {
        int numberOfTasks = originalTaskList.length;

        // Copy the inicial array, so that [runBenchmark] function can modify
        // it with no worries. Copy constructor does full copy.
        Task[] mutableTaskList = new Task[numberOfTasks];
        for (int i = 0; i < numberOfTasks; ++i) {
            mutableTaskList[i] = new Task(originalTaskList[i]);
        }

        tasksToAdd.addAll(Arrays.asList(mutableTaskList));

        int time = 0;
        while (!hasFinished()) {
            while (!tasksToAdd.isEmpty() &&
                    tasksToAdd.peek().getAppearanceTime() <= time) {
                addNewTask(tasksToAdd.poll(), time);
            }

            simulateOneSecond(time);

            time++;
        }
        assert tasksToAdd.isEmpty();

        return new BenchmarkInfo(mutableTaskList);
    }
}
