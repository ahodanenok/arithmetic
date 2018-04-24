package ahodanenok.arithmetic.exception;

import ahodanenok.arithmetic.Function;
import ahodanenok.arithmetic.Operator;

public class NotEnoughArguments extends RuntimeException {

    public NotEnoughArguments(Function fn, int expected, int actual) {
        super(String.format("Not enough arguments for function '%s': expected=%d, actual=%d", fn.getIdentifier(), expected, actual));
    }

    public NotEnoughArguments(Operator op, int expected, int actual) {
        super(String.format("Not enough arguments for operator '%s': expected=%d, actual=%d", op.getIdentifier(), expected, actual));
    }
}
