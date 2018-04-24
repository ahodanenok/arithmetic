package ahodanenok.arithmetic.operator;

import ahodanenok.arithmetic.Operator;

import java.math.BigDecimal;

public class DivideOperator extends Operator {

    public DivideOperator() {
        super("/", 2, 14, true);
    }

    @Override
    public BigDecimal evaluate(BigDecimal[] args) {
        BigDecimal result = args[0];
        for (int i = 1; i < args.length; i++) {
            if (BigDecimal.ZERO.equals(args[i])) {
                throw new ArithmeticException("Division by zero");
            }

            result = result.divide(args[i], mc);
        }

        return result;
    }
}
