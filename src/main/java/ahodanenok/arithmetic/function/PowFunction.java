package ahodanenok.arithmetic.function;

import ahodanenok.arithmetic.Function;

import java.math.BigDecimal;

public class PowFunction extends Function {

    public PowFunction() {
        super("pow", 2, 2);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        int n = args[1].intValueExact();
        return args[0].pow(n, mc);
    }
}
