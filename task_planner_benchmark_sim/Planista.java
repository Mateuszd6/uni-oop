import java.io.*;
import java.util.ArrayList;

public class Planista {

    // Message displayed when could not open a file.
    private static String inputFileMissingErrorMsg = "Plik z danymi nie " +
            "jest dostępny.";

    // Message displayed when there is an invalid input.
    // %d is for line number, and %s is for additional info.
    private static String inputErrorInLineErrMsg = "Błąd w wierszu %d: %s";

    private static void exitWithErrorMsg(int appExitCode, String msg) {
        System.out.println(msg);
        System.exit(appExitCode);
    }

    public static void main(String[] args) {
        BufferedReader inputReader = null;

        if (args.length > 0) {
            try {
                // Absolute path must be computed with some reason...
                String absolute = new File(args[0]).getAbsolutePath();
                inputReader = new BufferedReader(new FileReader(absolute));
            } catch (FileNotFoundException e) {
                exitWithErrorMsg(1, inputFileMissingErrorMsg);
            }
        } else
            inputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            InputData inputData = new InputData(inputReader);

            // Make a list of processors we want to check.
            ArrayList<Strategy> strategiesToCheck = new ArrayList<>();
            strategiesToCheck.add(new StrategyFCFS());
            strategiesToCheck.add(new StrategySJF());
            strategiesToCheck.add(new StrategySRT());
            strategiesToCheck.add(new StrategyProcessorSharing());
            for (int q : inputData.getqToCheck())
                strategiesToCheck.add(new StrategyRoundRobin(q));

            // And a list of benchmarking methods (possibly more in the future)!
            ArrayList<SimulationBenchmark> benchmarkMethods = new ArrayList<>();
            benchmarkMethods.add(new AvarageTaskTurnoverBenchmark());
            benchmarkMethods.add(new AvarageWaitingTimeBenchmark());

            // Make simulations and benchmarking:
            for (Strategy s : strategiesToCheck) {
                BenchmarkInfo bi = s.benchmark(inputData.getTasks());

                System.out.println(String.format("Strategia: %s", s.toString()));
                System.out.println(bi.toString());
                for (SimulationBenchmark b : benchmarkMethods)
                    System.out.println(b.generateBenchmarkRaport(bi));
                System.out.println();
            }
        } catch (IOLineNumberException e) {
            exitWithErrorMsg(2, String.format(inputErrorInLineErrMsg,
                    e.getLineNumber(), e.getMessage()));
        }
    }

}