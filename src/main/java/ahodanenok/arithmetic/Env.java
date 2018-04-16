package ahodanenok.arithmetic;

import java.util.HashSet;
import java.util.Set;

public class Env {

    Set<Function> functions;
    Set<Operator> operators;

    public Env() {
        this.functions = new HashSet<Function>();
        this.operators = new HashSet<Operator>();
    }

    void registerFunction(Function function) {
        if (functions.contains(function)) {
            throw new IllegalStateException(String.format(
                    "Function '%s' with %d parameters already registered",
                    function.getIdentifier(), function.getParametersCount()));
        }

        functions.add(function);
    }

    Function getFunction(String identifier, int parametersCount) {
        for (Function f : functions) {
            if (f.getIdentifier().equals(identifier) && f.getParametersCount() == parametersCount) {
                return f;
            }
        }

        return null;
    }

    void registerOperator(Operator operator) {
        if (operators.contains(operator)) {
            throw new IllegalStateException(String.format(
                    "Operator '%s' with %d parameters already registered",
                    operator.getIdentifier(), operator.getParametersCount()));
        }

        operators.add(operator);
    }

    Operator getOperator(String identifier, int parametersCount) {
        // first search for operator with the exact number of args
        for (Operator op : operators) {
            if (op.getIdentifier().equals(identifier) && !op.isVariableArgs() && op.accepts(parametersCount)) {
                return op;
            }
        }

        for (Operator op : operators) {
            if (op.getIdentifier().equals(identifier) && op.accepts(parametersCount)) {
                return op;
            }
        }

        return null;
    }
}
