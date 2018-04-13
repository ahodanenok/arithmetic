package ahodanenok.arithmetic;

import java.math.BigDecimal;

public class Arithmetic {

    private Notation notation;

    public Arithmetic(Notation notation) {
        if (notation == null) {
            throw new IllegalArgumentException("Notation is null");
        }

        this.notation = notation;
    }

    /**
     *
     * @throws InvalidExpressionException
     * @throws ArithmeticException
     *
     * @param expr
     * @return value of the given expression
     */
    public BigDecimal evaluate(String expr) {
        if (expr == null) {
            throw new IllegalArgumentException("Expression is null");
        }

        Tokenizer tokenizer = new Tokenizer(expr);
        NotationAstBuilder astBuilder = notation.createAstBuilder();

        try {
            Token token;
            while ((token = tokenizer.next()) != null) {
                astBuilder.addToken(token);
            }
        } catch (MismatchedParenthesisException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        }

        return astBuilder.build().evaluate();
    }
}
