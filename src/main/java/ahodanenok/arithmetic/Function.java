package ahodanenok.arithmetic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class Function {

    private static final int VARIABLES_ARGS = -1;

    private final String identifier;
    private final int minParametersCount;
    private final int parametersCount;

    protected MathContext mc;

    public Function(String identifier, int minParametersCount) {
        this(identifier, minParametersCount, VARIABLES_ARGS);
    }

    public Function(String identifier, int minParametersCount, int parametersCount) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier is null");
        }

        if (minParametersCount <= 0) {
            throw new IllegalArgumentException("Minimum parameters count must be greater than 0");
        }

        if (parametersCount <= 0 && parametersCount != VARIABLES_ARGS) {
            throw new IllegalArgumentException("Parameters count must be greater than 0");
        }

        if (minParametersCount > parametersCount && parametersCount != VARIABLES_ARGS) {
            throw new IllegalArgumentException("Minimum parameters count must be lower or equal to parametersCount");
        }

        if (!Syntax.isValidFunctionIdentifier(identifier)) {
            throw new IllegalArgumentException("'" + identifier + "' is not valid function name");
        }

        this.identifier = identifier;
        this.minParametersCount = minParametersCount;
        this.parametersCount = parametersCount;
        this.mc = new MathContext(16, RoundingMode.HALF_UP);
    }

    public final String getIdentifier() {
        return identifier;
    }

    public final int getParametersCount() {
        return parametersCount;
    }

    public final boolean isVariableArgs() {
        return getParametersCount() == VARIABLES_ARGS;
    }

    public final boolean accepts(int parametersCount) {
        return parametersCount >= minParametersCount
                && (parametersCount <= this.parametersCount || this.parametersCount == VARIABLES_ARGS);
    }

    protected void setMathContext(MathContext mc) {
        this.mc = mc;
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
