package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.*;
import ahodanenok.arithmetic.function.AbsFunction;
import ahodanenok.arithmetic.function.MaxFunction;
import ahodanenok.arithmetic.function.PowFunction;
import ahodanenok.arithmetic.operator.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class Arithmetic {

    public static Arithmetic createDefault(Notation notation) {
        Arithmetic arithmetic = create(notation);

        arithmetic.setMathContext(new MathContext(16, RoundingMode.HALF_UP));

        arithmetic.registerOperator(new DecrementOperator());
        arithmetic.registerOperator(new DivideOperator());
        arithmetic.registerOperator(new IncrementOperator());
        arithmetic.registerOperator(new MultiplyOperator());
        arithmetic.registerOperator(new NegateOperator());
        arithmetic.registerOperator(new PlusOperator());
        arithmetic.registerOperator(new RemainderOperator());
        arithmetic.registerOperator(new SubtractOperator());
        arithmetic.registerOperator(new SumOperator());

        arithmetic.registerFunction(new AbsFunction());
        arithmetic.registerFunction(new MaxFunction());
        arithmetic.registerFunction(new PowFunction());

        return arithmetic;
    }

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
        } catch (NotEnoughArguments e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        } catch (InvalidSyntaxException e) {
            throw new InvalidExpressionException(tokenizer.currentPosition(), tokenizer.currentLine(), expr, e);
        }

        BigDecimal result = ast.evaluate();

        Debug.newLine();
        Debug.log("Result = " + result);
        Debug.newLine();

        return result;
    }
}
