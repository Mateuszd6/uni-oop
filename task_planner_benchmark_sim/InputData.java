import java.io.BufferedReader;
import java.io.IOException;

/**
 * Object that contains all input that is needed to the program to work.
 * It is constructed with a BufferedReader which can be constructed either from
 * stdin, or a file.
 */
public class InputData {

    // The list of the tasks for processors to check.
    private Task[] tasks;

    // Array of q's to check for RR strategy.
    private int[] qToCheck;

    // Current line number in the buffer. Its necesarry to inform user at which
    // line the reader found an error.
    private int currentLine;

    public InputData(BufferedReader in) throws IOLineNumberException {
        currentLine = 0;

        int numberOfTasks = getIntsFromNextLine(in, 1)[0];
        Task[] tasks = new Task[numberOfTasks];

        for (int i = 0; i < numberOfTasks; ++i) {
            int[] taskParams = getIntsFromNextLine(in, 2);
            tasks[i] = new Task(i + 1, taskParams[0], taskParams[1]);

            if (taskParams[0] < 0 || taskParams[1] <= 0)
                throw new IOLineNumberException("Zły format. " +
                        "Pierwsza liczba musi być nieujemna, druga dodatnia.",
                        currentLine);
        }

        int numberOfQsToCheck = getIntsFromNextLine(in, 1)[0];
        int qToCheck[] = numberOfQsToCheck > 0
                ? getIntsFromNextLine(in, numberOfQsToCheck)
                : new int[0];

        for (int q : qToCheck)
            if (q <= 0)
                throw new IOLineNumberException("Zły format. " +
                        "Wartości parametru q dla strategii RR muszą być " +
                        "dodatanie!",
                        currentLine);

        this.tasks = tasks;
        this.qToCheck = qToCheck;
    }

    private int[] getIntsFromNextLine(BufferedReader reader,
                                      int numberOfValues)
            throws IOLineNumberException {

        assert reader != null;
        assert numberOfValues > 0;

        currentLine++;

        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            // Change IOException to our exception type so we can inform user at
            // which line the error happend.
            throw new IOLineNumberException(
                    "Nastąpił niespodziwany błąd. Plik przestał być czytelny.",
                    currentLine);
        }

        if (line == null)
            throw new IOLineNumberException(
                    "Niespodziewany koniec danych wejściowych.",
                    currentLine);

        int[] result = new int[numberOfValues];
        // Split line with all whitespace characters.
        String[] inputElements = line.trim().split("\\s+");

        if (inputElements.length != numberOfValues)
            throw new IOLineNumberException(
                    String.format(
                            "Zła ilość liczb, spodziewane: %d, otrzymane: %d.",
                            numberOfValues, inputElements.length),
                    currentLine);

        try {
            // NOTE: This might throw NumberFormatException if strings are not
            // numbers!
            for (int i = 0; i < numberOfValues; ++i) {
                result[i] = Integer.parseInt(inputElements[i]);
            }
        } catch (NumberFormatException e) {
            throw new IOLineNumberException("Zły format. " +
                    "Oczekiwano dodatniej liczby całkowitej.", currentLine);
        }
        return result;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public int[] getqToCheck() {
        return qToCheck;
    }

}
