package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;

abstract class NotationAstBuilder {

    Env env;
    NumberFactory numberFactory;

    void setEnv(Env env) {
        this.env = env;
    }

    void setNumberFactory(NumberFactory numberFactory) {
        this.numberFactory = numberFactory;
    }

    /**
     * @throws InvalidExpressionException
     * @param token
     */
    abstract void addToken(Token token);

    abstract Expression build();
}
