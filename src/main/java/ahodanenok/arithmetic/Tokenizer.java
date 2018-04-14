package ahodanenok.arithmetic;

import ahodanenok.arithmetic.exception.InvalidExpressionException;

class Tokenizer {

    private String expr;
    private int line;
    private int pos;
    private int idx;
    private char currentChar;
    private Token token;

    public Tokenizer(String expr) {
        if (expr == null) {
            throw new IllegalArgumentException("Expression is null");
        }

        this.expr = expr;
        this.line = 0;
        this.pos = 0;
        this.idx = 0;
        if (idx < expr.length()) {
            this.currentChar = expr.charAt(0);
        }
    }

    public Token next() throws InvalidExpressionException {
        token = null;
        skipWhitespaces();

        if (!hasMoreChars()) {
            return null;
        }

        if (currentChar == ',') {
            if (!readNextChar()) {
                return null;
            }

            skipWhitespaces();
            if (!hasMoreChars()) {
                return null;
            }
        }

        if (currentChar == '(') {
            token = new Token(TokenType.LP);
            readNextChar();
        } else if (currentChar == ')') {
            token = new Token(TokenType.RP);
            readNextChar();
        } else if (Syntax.isNumberStart(currentChar)) {
            StringBuilder value = new StringBuilder();
            value.append(currentChar);
            while (readNextChar() && Syntax.isNumberPart(currentChar)) {
                Syntax.checkNumber(value, currentChar);
                value.append(expr.charAt(idx));
            }

            token = new Token(TokenType.NUMBER, value.toString());
        } else if (Syntax.isOperatorStart(currentChar)) {
            StringBuilder value = new StringBuilder();
            value.append(currentChar);
            while (readNextChar() && Syntax.isOperatorPart(currentChar)) {
                value.append(expr.charAt(idx));
            }

            token = new Token(TokenType.OPERATOR, value.toString());
        } else if (Syntax.isFunctionStart(currentChar)) {
            StringBuilder value = new StringBuilder();
            value.append(currentChar);
            while (readNextChar() && Syntax.isFunctionPart(currentChar)) {
                value.append(expr.charAt(idx));
            }

            token = new Token(TokenType.FUNCTION, value.toString());
        } else {
            throw new InvalidExpressionException(pos, line, expr, "Illegal character: " + currentChar);
        }

        return token;
    }

    private boolean readNextChar() {
        idx++;
        pos++;
        if (hasMoreChars()) {
            currentChar = expr.charAt(idx);
            return true;
        } else {
            return false;
        }
    }

    private void skipWhitespaces() {
        while (hasMoreChars() && Character.isWhitespace(currentChar)) {
            readNextChar();
        }
    }

    private boolean hasMoreChars() {
        return idx < expr.length();
    }

    public int currentPosition() {
        return pos;
    }

    public int currentLine() {
        return line;
    }

    public Token currentToken() {
        return token;
    }
}
