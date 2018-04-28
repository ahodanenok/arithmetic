package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class DecrementOperator extends Operator {

    public DecrementOperator() {
        super("--", 1, 1, 15, true);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].subtract(BigDecimal.ONE, mc);
    }
}
