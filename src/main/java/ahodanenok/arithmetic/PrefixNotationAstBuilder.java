package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PrefixNotationAstBuilder implements NotationAstBuilder {

    private List<Token> tokens;

    public PrefixNotationAstBuilder() {
        this.tokens = new ArrayList<Token>();
    }

    @Override
    public void addToken(Token token) {
        this.tokens.add(token);
    }

    @Override
    public Expression build() {
        LinkedList<BigDecimal> numbers = new LinkedList<BigDecimal>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (token.getType() == TokenType.NUMBER) {
                numbers.push(new BigDecimal(token.getValue()));
            } else if (token.getType() == TokenType.LP) {

            } else if (token.getType() == TokenType.RP) {

            } else if (token.getType() == TokenType.OPERATOR) {

            } else if (token.getType() == TokenType.FUNCTION) {

            } else {
                throw new IllegalArgumentException("Unknown token type: " + token);
            }
        }

        return null;
    }
}
