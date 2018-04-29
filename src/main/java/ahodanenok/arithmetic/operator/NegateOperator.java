package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class NegateOperator extends Operator {

    public NegateOperator() {
        super("-", 1, 1, 20, false);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].negate(mc);
    }
}
