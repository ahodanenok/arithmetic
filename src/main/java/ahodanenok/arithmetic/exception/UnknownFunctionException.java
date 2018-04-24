package ahodanenok.arithmetic.exception;

public class UnknownFunctionException extends RuntimeException {

    private String identifier;

    public UnknownFunctionException(String identifier, int parametersCount) {
        super("No suitable function '" + identifier + "' with " + parametersCount + " parameter(s)");
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
