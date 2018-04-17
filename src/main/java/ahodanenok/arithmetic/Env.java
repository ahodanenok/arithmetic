package ahodanenok.arithmetic;

import java.util.LinkedHashSet;
import java.util.Set;

class Env {

    Set<Function> functions;
    Set<Operator> operators;

    Env() {
        this.functions = new LinkedHashSet<Function>();
        this.operators = new LinkedHashSet<Operator>();
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
        assert parametersCount > 0;
        // first search for operator with the exact number of args
        for (Function fn : functions) {
            if (fn.getIdentifier().equals(identifier) && !fn.isVariableArgs() && fn.accepts(parametersCount)) {
                return fn;
            }
        }

        for (Function fn : functions) {
            if (fn.getIdentifier().equals(identifier) && fn.accepts(parametersCount)) {
                return fn;
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
        assert parametersCount > 0;
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
