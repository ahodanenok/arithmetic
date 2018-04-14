package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;

interface NotationAstBuilder {

    /**
     * @throws InvalidExpressionException
     * @param token
     */
    void addToken(Token token);

    Expression build();
}
