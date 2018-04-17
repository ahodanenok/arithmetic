package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class MultiplyOperator extends Operator {

    public MultiplyOperator() {
        super("*", 2);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        BigDecimal result = args[0];
        for (int i = 1; i < args.length; i++) {
            result = result.multiply(args[i], mc);
        }

        return result;
    }
}
