package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class RemainderOperator extends Operator {

    public RemainderOperator() {
        super("%", 2, 2, 14, true);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].remainder(args[1], mc);
    }
}
