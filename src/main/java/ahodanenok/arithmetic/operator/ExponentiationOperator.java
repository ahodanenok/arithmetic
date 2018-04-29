package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class ExponentiationOperator extends Operator {

    public ExponentiationOperator() {
        super("**", 2, 2, 15, false);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        int n = args[1].intValueExact();
        return args[0].pow(n, mc);
    }
}
