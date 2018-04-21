package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.exception.InvalidExpressionException;
import ahodanenok.arithmetic.exception.UnknownFunctionException;
import ahodanenok.arithmetic.exception.UnknownOperatorException;
import ahodanenok.arithmetic.function.AbsFunction;
import ahodanenok.arithmetic.function.MaxFunction;
import ahodanenok.arithmetic.function.PowFunction;
import ahodanenok.arithmetic.operator.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PostfixNotationTest {

    @Test
    public void testAstBinaryOp() {
        // 1 2 +
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new SumOperator());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "1"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.OPERATOR, "+"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(3), expression.evaluate());
    }

    @Test
    public void testAstUnaryOp_1() {
        // 10 -
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(-10), expression.evaluate());
    }

    @Test
    public void testAstUnaryOp_2() {
        // 10 - - - - - -
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(-10), expression.evaluate());
    }

    @Test
    public void testAstUnaryBinaryOp() {
        // 2 - - 3 4 - -
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "3"));
        builder.addToken(new Token(TokenType.NUMBER, "4"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(5), expression.evaluate());
    }

    @Test
    public void testFnEval_UnknownOp() {
        Arithmetic arithmetic = Arithmetic.create(Notation.POSTFIX);
        arithmetic.registerOperator(new SumOperator());

        try {
            arithmetic.evaluate("2 5 - +");
            assertTrue(false);
        } catch (InvalidExpressionException e) {
            assertEquals(UnknownOperatorException.class, e.getCause().getClass());
            assertEquals("-", ((UnknownOperatorException) e.getCause()).getIdentifier());
        }
    }

    @Test
    public void testOpEval() {
        Arithmetic arithmetic = Arithmetic.create(Notation.POSTFIX);
        arithmetic.registerOperator(new SubtractOperator());
        arithmetic.registerOperator(new NegateOperator());
        arithmetic.registerOperator(new SumOperator());
        arithmetic.registerOperator(new DivideOperator());
        arithmetic.registerOperator(new MultiplyOperator());

        assertEquals(new BigDecimal(8), arithmetic.evaluate("10 2 -"));
        assertEquals(new BigDecimal(12), arithmetic.evaluate("10 2 +"));
        assertEquals(new BigDecimal(5), arithmetic.evaluate("10 2 /"));
        assertEquals(new BigDecimal(20), arithmetic.evaluate("10 2 *"));

        assertEquals(new BigDecimal(56), arithmetic.evaluate("10 2 - (3 4 +) *"));
        assertEquals(new BigDecimal(56), arithmetic.evaluate("(10 2 -) (3 4 +) *"));
        assertEquals(new BigDecimal(-5), arithmetic.evaluate("5 - - - - -"));
        assertEquals(new BigDecimal(-148), arithmetic.evaluate("(4 -) 3 (7 ( 2 ( 4 5 +) 8 * ) 10 -) +"));

        assertEquals(new BigDecimal(87), arithmetic.evaluate("(3 (10 5 -) (4 (2 1 +) 3 (8 6 -) *) 7 +)"));
    }

    @Test
    public void testAstBinaryFn() {
        // 2 5 pow
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerFunction(new PowFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.NUMBER, "5"));
        builder.addToken(new Token(TokenType.FUNCTION, "pow"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(32), expression.evaluate());
    }

    @Test
    public void testAstUnaryFn_1() {
        // 10 abs
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerFunction(new AbsFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "-10"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(10), expression.evaluate());
    }

    @Test
    public void testAstUnaryFn_2() {
        // 10 abs - abs - abs - abs
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerFunction(new AbsFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "-10"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(10), expression.evaluate());
    }

    @Test
    public void testAstUnaryBinaryFn() {
        // -2 5 pow abs
        PostfixNotationAstBuilder builder = new PostfixNotationAstBuilder();
        Env env = new Env();
        env.registerFunction(new AbsFunction());
        env.registerFunction(new PowFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.NUMBER, "-2"));
        builder.addToken(new Token(TokenType.NUMBER, "5"));
        builder.addToken(new Token(TokenType.FUNCTION, "pow"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(32), expression.evaluate());
    }

    @Test
    public void testFnEval_UnknownFn() {
        Arithmetic arithmetic = Arithmetic.create(Notation.POSTFIX);
        arithmetic.registerFunction(new AbsFunction());

        try {
            arithmetic.evaluate("2 5 pow abs");
            assertTrue(false);
        } catch (InvalidExpressionException e) {
            assertEquals(UnknownFunctionException.class, e.getCause().getClass());
            assertEquals("pow", ((UnknownFunctionException) e.getCause()).getIdentifier());
        }
    }

    @Test
    public void testFnEval() {
        Arithmetic arithmetic = Arithmetic.create(Notation.POSTFIX);
        arithmetic.registerFunction(new AbsFunction());
        arithmetic.registerFunction(new PowFunction());
        arithmetic.registerFunction(new MaxFunction());
        arithmetic.registerOperator(new NegateOperator());

        assertEquals(new BigDecimal(1024), arithmetic.evaluate("2- abs 10- abs pow"));
        assertEquals(new BigDecimal(7), arithmetic.evaluate("4- abs 7- abs 3 2 4 5 max max"));
        assertEquals(new BigDecimal(8), arithmetic.evaluate("4- abs 7- abs 3 2 8 5 max max"));

        assertEquals(new BigDecimal(1024), arithmetic.evaluate("((2 abs) (2 5 pow) 7 8 (20 (2 10 pow) max) max)"));
    }
}
