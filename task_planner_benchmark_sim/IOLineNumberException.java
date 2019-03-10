import java.io.IOException;

/**
 * This exception is throwed by InputData constructor to indicate, at which
 * line an error has happend.
 */
public class IOLineNumberException extends IOException {
    private int lineNumber;

    public IOLineNumberException(String msg, int lineNr) {
        super(msg);
        this.lineNumber = lineNr;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
