package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class SumOperator extends Operator {

    public SumOperator() {
        super("+", 2);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        BigDecimal result = args[0];
        for (int i = 1; i < args.length; i++) {
            result = result.add(args[i], mc);
        }

        return result;
    }
}
