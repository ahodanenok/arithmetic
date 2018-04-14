package ahodanenok.arithmetic.exception;

public class InvalidExpressionException extends RuntimeException {

    private int pos;
    private int line;
    private String expr;

    public InvalidExpressionException(int pos, int line, String expr, String cause) {
        super(cause);
        this.pos = pos;
        this.line = line;
        this.expr = expr;
    }

    public InvalidExpressionException(int pos, int line, String expr, Exception cause) {
        super("Invalid expression", cause);
        this.pos = pos;
        this.line = line;
        this.expr = expr;
    }
}
