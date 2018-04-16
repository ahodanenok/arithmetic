package ahodanenok.arithmetic.ast;

import java.math.BigDecimal;

public class NumberExpression implements Expression {

    private BigDecimal number;

    public NumberExpression(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getNumber() {
        return number;
    }

    @Override
    public Expression[] getArgs() {
        return new Expression[0];
    }

    @Override
    public BigDecimal evaluate() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberExpression {number=" + number + "}";
    }
}
