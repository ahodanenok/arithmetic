package ahodanenok.arithmetic.function;

import ahodanenok.arithmetic.Function;

import java.math.BigDecimal;

public class MaxFunction extends Function {

    public MaxFunction() {
        super("max", 1);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        BigDecimal max = args[0];
        for (BigDecimal arg : args) {
            if (max.compareTo(arg) < 0) {
                max = arg;
            }
        }

        return max;
    }
}
