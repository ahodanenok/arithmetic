package ahodanenok.arithmetic.ast;

import ahodanenok.arithmetic.Function;

import java.math.BigDecimal;
import java.util.Arrays;

public class FunctionExpression implements Expression {

    private Function fn;
    private Expression[] args;

    public FunctionExpression(Function fn, Expression[] args) {
        this.fn = fn;
        this.args = args;
    }

    public Function getFn() {
        return fn;
    }

    @Override
    public Expression[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }

    @Override
    public BigDecimal evaluate() {
        BigDecimal[] numbers = new BigDecimal[args.length];
        // left to right evaluation
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = args[i].evaluate();
        }

        return fn.evaluate(numbers);
    }

    @Override
    public String toString() {
        return "FunctionExpression: { fn=" + fn + ", args=" + Arrays.toString(args) + "}";
    }
}
