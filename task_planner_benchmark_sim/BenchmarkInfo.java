import java.util.Arrays;
import java.util.Locale;

/**
 * Contains all important data about the processor strategy simulation.
 * As said in the task description, this is enought data for any benchhmark
 * algorithm.
 */
public class BenchmarkInfo {

    private Task[] taskList;

    public BenchmarkInfo(Task[] taskList) {
        this.taskList = Arrays.copyOf(taskList, taskList.length);

        Arrays.sort(this.taskList, (x, y) ->
                (int) Math.signum(x.getFinishTime() - y.getFinishTime() != 0
                        ? (int) Math.signum(x.getFinishTime() - y.getFinishTime())
                        : x.getId() - y.getId()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task t : taskList) {
            // NOTE: Format: [1 0 10.00]. Java, with absolutely no reason to do so,
            // tries to add ',' istead of '.' on a PLD (???) linux I guess,
            // certainly not on my machine, but setting Locale to US seems to
            // fix it (at least I hope so)!
            sb.append(String.format(Locale.US, "[%d %d %.2f]",
                    t.getId(), t.getAppearanceTime(),
                    t.getFinishTime()));
        }

        return sb.toString();
    }

    public float getQueueAppearanceTimes(int i) {
        return taskList[i].getAppearanceTime();
    }

    public double getFinishTimes(int i) {
        return taskList[i].getFinishTime();
    }

    public double getWaitingTimes(int i) {
        return taskList[i].getTotalWaitingTime();
    }

    public int getNumberOfTasks() {
        return taskList.length;
    }


}

