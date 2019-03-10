public class MissingPropertiesFileException extends Exception {

    private String missingConfigFileName;

    public String getMissingConfigFileName() {
        return missingConfigFileName;
    }

    public MissingPropertiesFileException(String missingConfigFileName)
    {
        this.missingConfigFileName = missingConfigFileName;
    }
}
