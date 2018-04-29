package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class DecrementOperator extends Operator {

    public DecrementOperator() {
        super("--", 1, 1, 20, false);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].subtract(BigDecimal.ONE, mc);
    }
}
