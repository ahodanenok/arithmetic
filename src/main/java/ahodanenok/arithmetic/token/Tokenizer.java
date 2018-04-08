package ahodanenok.arithmetic.token;

import ahodanenok.arithmetic.InvalidExpressionException;

public class Tokenizer {

    private String expr;

    public Tokenizer(String expr) {
        // todo: check arg
        this.expr = expr;
    }

    public Token next() throws InvalidExpressionException {
        return null;
    }

    public int currentPosition() {
        return 0;
    }

    public int currentLine() {
        return 0;
    }

    public Token currentToken() {
        return null;
    }
}
