/**
 * Very simple strategy; tasks are selected based on the queue appearance time.
 */
public class StrategyFCFS extends StrategyPriorityBased {

    private static final String name = "FCFS";

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected int compare(Task x, Task y) {
        int timeDiff = x.getAppearanceTime() - y.getAppearanceTime();

        return timeDiff != 0 ? timeDiff : x.getId() - y.getId();
    }

}
