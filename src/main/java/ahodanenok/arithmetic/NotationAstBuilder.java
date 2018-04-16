package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;

abstract class NotationAstBuilder {

    protected Env env;

    void setEnv(Env env) {
        this.env = env;
    }

    /**
     * @throws InvalidExpressionException
     * @param token
     */
    abstract void addToken(Token token);

    abstract Expression build();
}
