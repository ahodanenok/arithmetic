package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;
import ahodanenok.arithmetic.exception.MismatchedParenthesisException;
import ahodanenok.arithmetic.exception.UnknownFunctionException;
import ahodanenok.arithmetic.exception.UnknownOperatorException;

import java.math.BigDecimal;

public class Arithmetic {

    public static Arithmetic create(Notation notation) {
        return new Arithmetic(notation);
    }

    private Notation notation;
    private Env env;

    private Arithmetic(Notation notation) {
        if (notation == null) {
            throw new IllegalArgumentException("Notation is null");
        }

        this.notation = notation;
        this.env = new Env();
    }

    public void registerFunction(Function function) {
        env.registerFunction(function);
    }

    public void registerOperator(Operator operator) {
        env.registerOperator(operator);
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
        if (expr == null) {
            throw new IllegalArgumentException("Expression is null");
        }

        Tokenizer tokenizer = new Tokenizer(expr);
        NotationAstBuilder astBuilder = notation.createAstBuilder();
        astBuilder.setEnv(env);

        Expression ast;
        try {
            Token token;
            while ((token = tokenizer.next()) != null) {
                astBuilder.addToken(token);
            }

            ast = astBuilder.build();
        } catch (MismatchedParenthesisException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        } catch (UnknownFunctionException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        } catch (UnknownOperatorException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        }

        return ast.evaluate();
    }
}
