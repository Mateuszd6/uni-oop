// Thrown if either a property has an invalid key (i.e. int property has text
// value), or if value is NULL, this exception that the property name is invalid
public class BadPropertyInConfigFileException extends Exception {

    private String argumentName;
    private String value;

    public String getArgumentName() {
        return argumentName;
    }

    public String getValue() {
        return value;
    }

    public BadPropertyInConfigFileException(String argumentName, String value) {
        this.argumentName = argumentName;
        this.value = value;
    }
}
