package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;
import ahodanenok.arithmetic.exception.MismatchedParenthesisException;
import ahodanenok.arithmetic.exception.UnknownFunctionException;
import ahodanenok.arithmetic.exception.UnknownOperatorException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Arithmetic {

    public static Arithmetic create(Notation notation) {
        return new Arithmetic(notation);
    }

    private Notation notation;
    private Env env;

    private MathContext mc;

    private Arithmetic(Notation notation) {
        if (notation == null) {
            throw new IllegalArgumentException("Notation is null");
        }

        this.notation = notation;
        this.env = new Env();
        this.mc = new MathContext(16, RoundingMode.HALF_UP);
    }

    public void registerFunction(Function function) {
        env.registerFunction(function);
        function.setMathContext(mc);
    }

    public void registerOperator(Operator operator) {
        env.registerOperator(operator);
        operator.setMathContext(mc);
    }

    public void setMathContext(MathContext mc) {
        this.mc = mc;
        for (Operator op : env.operators) {
            op.setMathContext(mc);
        }
        for (Function fn : env.functions) {
            fn.setMathContext(mc);
        }
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
        astBuilder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number, mc);
            }
        });

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

        BigDecimal result = ast.evaluate();

        Debug.newLine();
        Debug.log("Result = " + result);
        Debug.newLine();

        return result;
    }
}
