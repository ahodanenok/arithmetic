package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class IncrementOperator extends Operator {

    public IncrementOperator() {
        super("++", 1, 1, 20, false);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].add(BigDecimal.ONE, mc);
    }
}
