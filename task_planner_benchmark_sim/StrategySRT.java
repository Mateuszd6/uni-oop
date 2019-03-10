/**
 * Strategy that selects same as SJF, but can swap current task and incomming
 * task, if the seccond one has shorter time requirements.
 */
public class StrategySRT extends StrategySJF {

    private static final String name = "SRT";

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void addNewTask(Task task, int currentTime) {
        if (currentTask != null && currentTask.getRemainingTime()
                > task.getTotalTime()) {

            currentTask.setLastQueueTime(currentTime);
            super.addNewTask(currentTask, currentTime);

            currentTask = task;
        } else
            super.addNewTask(task, currentTime);
    }

}
