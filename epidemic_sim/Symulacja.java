import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class Symulacja {

    public static void main(String[] args) {
        String defaultPropertiesFileName = "default.properties";
        String overridePropertiesFileName = "simulation-conf.xml";


        SimulationData simulationData = null;
        try {
            simulationData = new SimulationData(defaultPropertiesFileName, overridePropertiesFileName);
        } catch (BadPropertyInConfigFileException e) {
            // If value is null this means that there was missing argument,
            // else this means that the key had an invalid value.
            if (e.getValue() != null)
                System.out.println("Niedozwolona wartość \"" +
                        e.getValue() + "\" dla klucza " + e.getArgumentName());
            else
                System.out.println("Niewłaściwy klucz w pliku konfiguracyjnym: " +
                        e.getArgumentName());

            System.exit(1);
        } catch (MissingPropertiesFileException e) {
            System.out.println("Brak pliku " + e.getMissingConfigFileName());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println(defaultPropertiesFileName + " nie jest tekstowy");
            System.exit(1);
        } catch (InvalidPropertiesFormatException e) {
            System.out.println(overridePropertiesFileName + " nie jest XML");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Niespodziewany błąd podczas wczytywania plików.");
            System.exit(2);
        }

        SimulationInstance s = new SimulationInstance(simulationData);
        s.runSimulation();
    }
}
