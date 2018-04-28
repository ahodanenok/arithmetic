package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class IncrementOperator extends Operator {

    public IncrementOperator() {
        super("++", 1, 1, 15, true);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].add(BigDecimal.ONE, mc);
    }
}
