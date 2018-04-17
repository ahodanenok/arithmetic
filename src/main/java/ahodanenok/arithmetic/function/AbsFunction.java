package ahodanenok.arithmetic.function;

import ahodanenok.arithmetic.Function;

import java.math.BigDecimal;

public class AbsFunction extends Function {

    public AbsFunction() {
        super("abs", 1, 1);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].abs();
    }
}
