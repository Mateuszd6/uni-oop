/**
 * Single task for the processor. finishTime, totalWaitingTime, and
 * lastQueueTime, are filled by the simulation function, if the task is
 * finished remainingTime is set to 0.
 */
public class Task {

    private int id;
    private int appearanceTime;
    private int totalTime;
    private double remainingTime;

    private double finishTime; // -1 means the task has not beed finished yet.
    private double totalWaitingTime;
    private int lastQueueTime;

    public Task(int id, int appearanceTime,
                int totalTime) {
        this.id = id;
        this.appearanceTime = appearanceTime;
        this.totalTime = totalTime;
        this.totalWaitingTime = 0.0;
        this.finishTime = -1.0;
        this.remainingTime = totalTime;
        this.lastQueueTime = 0;
    }

    public Task(Task other) {
        this(other.id, other.appearanceTime,
                other.totalTime);
    }

    public int getAppearanceTime() {
        return appearanceTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(double finishTime) {
        this.finishTime = finishTime;
    }

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setTotalWaitingTime(double totalWaitingTime) {
        this.totalWaitingTime = totalWaitingTime;
    }

    public int getLastQueueTime() {
        return lastQueueTime;
    }

    public void setLastQueueTime(int lastQueueTime) {
        this.lastQueueTime = lastQueueTime;
    }

    public int getId() {

        return id;

    }
}
