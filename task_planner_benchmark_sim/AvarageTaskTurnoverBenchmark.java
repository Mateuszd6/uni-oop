import java.util.Locale;

/**
 * Benchmark that calculates the avarage task turnover.
 */
public class AvarageTaskTurnoverBenchmark extends SimulationBenchmark {

    private static final String messageText = "Średni czas obrotu";

    @Override
    public String generateBenchmarkRaport(BenchmarkInfo info) {

        double result = 0;
        for (int i = 0; i < info.getNumberOfTasks(); ++i)
            result += info.getFinishTimes(i) - info.getQueueAppearanceTimes(i);

        result /= info.getNumberOfTasks();
        return String.format(Locale.US, "%s: %.2f", messageText, result);
    }
}
