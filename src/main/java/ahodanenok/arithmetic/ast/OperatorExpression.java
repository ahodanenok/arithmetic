package ahodanenok.arithmetic.ast;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;
import java.util.Arrays;

public class OperatorExpression implements Expression {

    private Operator op;
    private Expression[] args;

    public OperatorExpression(Operator op, Expression[] args) {
        this.op = op;
        this.args = args;
    }

    public Operator getOp() {
        return op;
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

        return op.evaluate(numbers);
    }

    @Override
    public String toString() {
        return "OperatorExpression(op = " + op + ")";
    }
}
