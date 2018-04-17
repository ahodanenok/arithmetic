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

        Debug.log("Arithmetic.evaluate");
        Debug.log("Evaluating in %s notation: '%s'", notation, expr);

        Debug.newLine();
        Debug.log("Env:");
        Debug.indent();
        Debug.logEnv(env);
        Debug.unindent();

        Tokenizer tokenizer = new Tokenizer(expr);
        NotationAstBuilder astBuilder = notation.createAstBuilder();
        astBuilder.setEnv(env);

        Expression ast;
        try {
            Debug.newLine();
            Debug.log("Parsing to tokens:");
            Debug.indent();
            Token token;
            while ((token = tokenizer.next()) != null) {
                Debug.logToken(token);
                astBuilder.addToken(token);
            }
            Debug.unindent();

            Debug.newLine();
            Debug.log("Building AST:");
            Debug.indent();
            ast = astBuilder.build();
            Debug.unindent();

            Debug.newLine();
            Debug.log("AST:");
            Debug.indent();
            Debug.logExpression(ast);
            Debug.unindent();
        } catch (MismatchedParenthesisException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        } catch (UnknownFunctionException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        } catch (UnknownOperatorException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        }

        Debug.newLine();
        return ast.evaluate();
    }
}
