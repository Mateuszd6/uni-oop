// Framework-free way to display log messages in the debug build and quickly
// remove them in the release.
public class Debug {
    // Don't print anything to the console in the release version:
    private static final boolean printDebugLogs = true;

    // Define private constructor so that no one can instantiate this class.
    private Debug() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static void log(String template, Object... objs) {
        if (printDebugLogs) {
            System.err.println(String.format(template, objs));
        } else {
            // Do nothing. I hope java compiler will optimize this part in the
            // build, because printDebugLogs is static final value...
        }
    }

    // Override clone so that no one can use clone.
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
