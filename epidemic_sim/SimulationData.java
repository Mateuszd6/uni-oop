import java.io.*;
import java.nio.charset.Charset;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class SimulationData {
    private int seed;
    private int numberOfAgents;
    private float meetingChances;
    private float infectingChances;
    private float cureChances;
    private float dyingChances;
    private int numberOfDays;
    private float friendsAverage;
    private float friendlyAgentChances;
    private String rapportFile;

    private Properties LoadProperties(String defaultPropertiesFileName,
                                      String overridePropertiesFileName)
            throws MissingPropertiesFileException,
            IllegalArgumentException,
            InvalidPropertiesFormatException,
            IOException {

        // At first we obtain the root path of the program resources.
        String rootPath = Thread.currentThread().getContextClassLoader().
                getResource("").getPath();

        String defaultConfigPath = rootPath + defaultPropertiesFileName;
        String overrideConfigPath = rootPath + overridePropertiesFileName;


        Properties config = new Properties();
        FileInputStream input;

        try {
            input = new FileInputStream(new File(defaultConfigPath));
        } catch (FileNotFoundException e) {
            throw new MissingPropertiesFileException(defaultPropertiesFileName);
        }

        try {
            config.load(new InputStreamReader(input, Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(22);
        }

        Properties overrideConfig = new Properties();

        try {
            input = new FileInputStream(new File(overrideConfigPath));
        } catch (FileNotFoundException e) {
            throw new MissingPropertiesFileException(overridePropertiesFileName);
        }

        overrideConfig.loadFromXML(input);

        for (String key : overrideConfig.stringPropertyNames()) {
            String value = overrideConfig.getProperty(key);
            Debug.log("Overriding property: %s with %s", key, value);
            config.setProperty(key, value);
        }

        return config;
    }

    public SimulationData(String defaultPropertiesFileName,
                          String overridePropertiesFileName)
            throws BadPropertyInConfigFileException,
            MissingPropertiesFileException,
            IllegalArgumentException,
            InvalidPropertiesFormatException,
            IOException {

        Properties props = LoadProperties(defaultPropertiesFileName, overridePropertiesFileName);

        for (String key : props.stringPropertyNames()) {
            String value = props.getProperty(key);
            // TODO: Co gdy brak wartości dla plikZRaportem? (spec)!!

            try {
                switch (key) {
                    case "seed":
                        this.seed = Integer.parseInt(value);
                        break;

                    case "liczbaAgentów":
                        this.numberOfAgents = Integer.parseInt(value);
                        break;

                    case "prawdSpotkania":
                        this.meetingChances = Float.parseFloat(value);
                        break;

                    case "prawdZarażenia":
                        this.infectingChances = Float.parseFloat(value);
                        break;

                    case "prawdWyzdrowienia":
                        this.cureChances = Float.parseFloat(value);
                        break;

                    case "śmiertelność":
                        this.dyingChances = Float.parseFloat(value);
                        break;

                    case "liczbaDni":
                        this.numberOfDays = Integer.parseInt(value);
                        break;

                    case "śrZnajomych":
                        this.friendsAverage = Integer.parseInt(value);
                        break;

                    case "prawdTowarzyski":
                        this.friendlyAgentChances = Float.parseFloat(value);
                        break;

                    case "plikZRaportem":
                        this.rapportFile = value;
                        break;

                    default:
                        // This happens when the property name is not recognized.
                        throw new BadPropertyInConfigFileException(key, null);
                }
            } catch (NumberFormatException e) {
                // This happens when we cannot covert property value to its type
                throw new BadPropertyInConfigFileException(key, value);
            }
        }
    }

    public float getFriendlyAgentChances() {
        return friendlyAgentChances;
    }

    public int getSeed() {
        return seed;
    }

    public int getNumberOfAgents() {
        return numberOfAgents;
    }

    public float getMeetingChances() {
        return meetingChances;
    }

    public float getInfectingChances() {
        return infectingChances;
    }

    public float getCureChances() {
        return cureChances;
    }

    public float getDyingChances() {
        return dyingChances;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public float getFriendsAverage() {
        return friendsAverage;
    }

    public String getRapportFile() {
        return rapportFile;
    }

    public String toString() {

        // Based on what I have read, this is more efficient that StringBuilder
        // https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18.1
        return "seed=" + seed + "\n" +
                "liczbaAgentów=" + numberOfAgents + "\n" +
                "prawdTowarzyski=" + friendlyAgentChances + "\n" +
                "prawdSpotkania=" + meetingChances + "\n" +
                "prawdZarażenia=" + infectingChances + "\n" +
                "prawdWyzdrowienia=" + cureChances + "\n" +
                "śmiertelność=" + dyingChances + "\n" +
                "liczbaDni=" + numberOfDays + "\n" +
                "śrZnajomych=" + friendsAverage + "\n" +
                "plikZRaportem=" + rapportFile + "\n";
    }
}