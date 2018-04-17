package ahodanenok.arithmetic.function;

import ahodanenok.arithmetic.Function;

import java.math.BigDecimal;

public class PowFunction extends Function {

    public PowFunction() {
        super("pow", 2, 2);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        return args[0].pow(args[1].intValue(), mc);
    }
}
