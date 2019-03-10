import java.util.Locale;

/**
 * Benchmark that calculates avarage of total waiting time of the tasks.
 */
public class AvarageWaitingTimeBenchmark extends SimulationBenchmark {

    private static final String messageText = "Średni czas oczekiwania";

    @Override
    public String generateBenchmarkRaport(BenchmarkInfo info) {
        int numberOfTasks = info.getNumberOfTasks();

        double result = 0;
        for (int i = 0; i < numberOfTasks; ++i)
            result += info.getWaitingTimes(i);

        result /= numberOfTasks;

        return String.format(Locale.US, "%s: %.2f", messageText, result);
    }

}
