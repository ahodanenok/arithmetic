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

public class PrefixNotationTest {

    @Test
    public void testAstBinaryOp() {
        // + 1 2
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new SumOperator());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.OPERATOR, "+"));
        builder.addToken(new Token(TokenType.NUMBER, "1"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(3), expression.evaluate());
    }

    @Test
    public void testAstUnaryOp_1() {
        // - 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
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
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(-10), expression.evaluate());
    }

    @Test
    public void testAstUnaryOp_2() {
        // - 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
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
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(-10), expression.evaluate());
    }

    @Test
    public void testAstUnaryBinaryOp() {
        // - - 2 - - 3 4
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
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
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "3"));
        builder.addToken(new Token(TokenType.NUMBER, "4"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(-1), expression.evaluate());
    }

    @Test
    public void testFnEval_UnknownOp() {
        Arithmetic arithmetic = Arithmetic.create(Notation.PREFIX);
        arithmetic.registerOperator(new SumOperator());

        try {
            arithmetic.evaluate("+ - 2 5");
            assertTrue(false);
        } catch (InvalidExpressionException e) {
            assertEquals(UnknownOperatorException.class, e.getCause().getClass());
            assertEquals("-", ((UnknownOperatorException) e.getCause()).getIdentifier());
        }
    }

    @Test
    public void testOpEval() {
        Arithmetic arithmetic = Arithmetic.create(Notation.PREFIX);
        arithmetic.registerOperator(new SubtractOperator());
        arithmetic.registerOperator(new NegateOperator());
        arithmetic.registerOperator(new SumOperator());
        arithmetic.registerOperator(new DivideOperator());
        arithmetic.registerOperator(new MultiplyOperator());

        assertEquals(new BigDecimal(8), arithmetic.evaluate("- 10 2"));
        assertEquals(new BigDecimal(12), arithmetic.evaluate("+ 10 2"));
        assertEquals(new BigDecimal(5), arithmetic.evaluate("/ 10 2"));
        assertEquals(new BigDecimal(20), arithmetic.evaluate("* 10 2"));

        assertEquals(new BigDecimal(56), arithmetic.evaluate("* (- 10 2) + 3 4"));
        assertEquals(new BigDecimal(56), arithmetic.evaluate("* (- 10 2) (+ 3 4)"));
        assertEquals(new BigDecimal(-5), arithmetic.evaluate("- - - - - 5"));
        assertEquals(new BigDecimal(-148), arithmetic.evaluate("+ (- 4) + 3 (- 7 (* 2 ( + 4 5 ) 8 ) 10)"));

        assertEquals(new BigDecimal(87), arithmetic.evaluate("(+ 3 (- 10 5) (* 4 (+ 2 1) 3 (- 8 6)) 7)"));
    }

    @Test
    public void testAstBinaryFn() {
        // pow 2 5
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerFunction(new PowFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.FUNCTION, "pow"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.NUMBER, "5"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(32), expression.evaluate());
    }

    @Test
    public void testAstUnaryFn_1() {
        // abs 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerFunction(new AbsFunction());
        builder.setEnv(env);
        builder.setNumberFactory(new NumberFactory() {
            @Override
            public BigDecimal create(String number) {
                return new BigDecimal(number);
            }
        });
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.NUMBER, "-10"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(10), expression.evaluate());
    }

    @Test
    public void testAstUnaryFn_2() {
        // abs - abs - abs - abs 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
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
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.NUMBER, "-10"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(10), expression.evaluate());
    }

    @Test
    public void testAstUnaryBinaryFn() {
        // abs pow -2 5
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
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
        builder.addToken(new Token(TokenType.FUNCTION, "abs"));
        builder.addToken(new Token(TokenType.FUNCTION, "pow"));
        builder.addToken(new Token(TokenType.NUMBER, "-2"));
        builder.addToken(new Token(TokenType.NUMBER, "5"));
        Expression expression = builder.build();

        assertEquals(new BigDecimal(32), expression.evaluate());
    }

    @Test
    public void testFnEval_UnknownFn() {
        Arithmetic arithmetic = Arithmetic.create(Notation.PREFIX);
        arithmetic.registerFunction(new AbsFunction());

        try {
            arithmetic.evaluate("abs pow 2 5");
            assertTrue(false);
        } catch (InvalidExpressionException e) {
            assertEquals(UnknownFunctionException.class, e.getCause().getClass());
            assertEquals("pow", ((UnknownFunctionException) e.getCause()).getIdentifier());
        }
    }

    @Test
    public void testFnEval() {
        Arithmetic arithmetic = Arithmetic.create(Notation.PREFIX);
        arithmetic.registerFunction(new AbsFunction());
        arithmetic.registerFunction(new PowFunction());
        arithmetic.registerFunction(new MaxFunction());
        arithmetic.registerOperator(new NegateOperator());

        assertEquals(new BigDecimal(1024), arithmetic.evaluate("pow abs -2 abs -10"));
        assertEquals(new BigDecimal(7), arithmetic.evaluate("max abs -4 abs -7 max 3 2 4 5"));
        assertEquals(new BigDecimal(8), arithmetic.evaluate("max abs -4 abs -7 max 3 2 8 5"));

        assertEquals(new BigDecimal(1024), arithmetic.evaluate("(max (abs 2) (pow 2 5) 7 8 (max 20 (pow 2 10)))"));
    }
}
