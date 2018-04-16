package ahodanenok.arithmetic;

import java.math.BigDecimal;

public abstract class Function {

    protected final String identifier;
    protected final int parametersCount;

    public Function(String identifier, int parametersCount) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier is null");
        }

        if (parametersCount <= 0) {
            throw new IllegalArgumentException("Parameters count must be greater than 0");
        }

        if (!Syntax.isValidFunctionIdentifier(identifier)) {
            throw new IllegalArgumentException("'" + identifier + "' is not valid function name");
        }

        this.identifier = identifier;
        this.parametersCount = parametersCount;
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final int getParametersCount() {
        return parametersCount;
    }

    public abstract BigDecimal evaluate(BigDecimal[] args);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Function function = (Function) o;

        if (parametersCount != function.parametersCount) return false;
        return identifier.equals(function.identifier);
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + parametersCount;
        return result;
    }

    @Override
    public String toString() {
        return "Function(" + identifier + ", " + parametersCount + ")";
    }
}
