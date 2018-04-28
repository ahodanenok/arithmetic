package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class PlusOperator extends Operator {

    public PlusOperator() {
        super("+", 1, 1, 15, true);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].plus(mc);
    }
}
