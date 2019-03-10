import java.util.ArrayList;

/**
 * Strategy that always share full resources across all tasks.
 */
public class StrategyProcessorSharing extends Strategy {

    private static final String name = "PS";
    private static final double EPS = 0.00000001;
    private ArrayList<Task> taskList;

    public StrategyProcessorSharing() {
        super();
        taskList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected void addNewTask(Task task, int currentTime) {
        taskList.add(task);
    }

    @Override
    protected boolean hasFinished() {
        return super.hasFinished() && taskList.isEmpty();
    }

    @Override
    protected void simulateOneSecond(int currentTime) {
        if (taskList.isEmpty())
            return;

        double currentFrameAdvanceBy = 1.0;
        double time = (double) currentTime;

        while (currentFrameAdvanceBy > EPS) {
            double advace_by = currentFrameAdvanceBy;
            for (int i = 0; i < taskList.size(); ++i) {
                Task currentTask = taskList.get(i);

                if (currentTask.getRemainingTime() * taskList.size()
                        - advace_by < EPS) {
                    advace_by = currentTask.getRemainingTime() * taskList.size();
                }
            }

            double singeTaskAdvanceTime = advace_by / taskList.size();
            for (int i = 0; i < taskList.size(); ++i) {
                Task currentTask = taskList.get(i);

                if (currentTask.getRemainingTime() - singeTaskAdvanceTime < EPS) {
                    currentTask.setFinishTime((float) time + (float) (advace_by));

                    currentTask.setRemainingTime(0);
                    taskList.remove(i);
                    i--;
                } else {
                    currentTask.setRemainingTime(currentTask.getRemainingTime() -
                            singeTaskAdvanceTime);
                }
            }

            time += advace_by;
            currentFrameAdvanceBy -= advace_by;
        }
    }
}
