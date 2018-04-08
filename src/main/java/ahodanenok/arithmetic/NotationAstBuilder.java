package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.token.Token;

public interface NotationAstBuilder {

    /**
     * @throws InvalidExpressionException
     * @param token
     */
    void addToken(Token token);

    Expression build();
}
