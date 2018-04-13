package ahodanenok.arithmetic;

import java.math.BigDecimal;

public class Arithmetic {

    private Notation notation;

    public Arithmetic(Notation notation) {
        // todo: check arg
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
        // todo: check arg

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
