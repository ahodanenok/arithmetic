package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;

public interface NotationAstBuilder {

    /**
     * @throws InvalidExpressionException
     * @param token
     */
    void addToken(Token token);

    Expression build();
}
