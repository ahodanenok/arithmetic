package ahodanenok.arithmetic.exception;

public class UnknownOperatorException extends RuntimeException {

    private String identifier;

    public UnknownOperatorException(String identifier, int parametersCount) {
        super("No suitable operator '" + identifier + "' with " + parametersCount + " parameter(s)");
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
