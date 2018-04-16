package ahodanenok.arithmetic;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.operator.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PrefixNotationTest {

    @Test
    public void testAstBinaryOp() {
        // + 1 2
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new SumOperator());
        builder.setEnv(env);
        builder.addToken(new Token(TokenType.OPERATOR, "+"));
        builder.addToken(new Token(TokenType.NUMBER, "1"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        Expression expression = builder.build();

        assertEquals(expression.evaluate(), new BigDecimal(3));
    }

    @Test
    public void testAstUnaryOp_1() {
        // - 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        Expression expression = builder.build();

        assertEquals(expression.evaluate(), new BigDecimal(-10));
    }

    @Test
    public void testAstUnaryOp_2() {
        // - 10
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "10"));
        Expression expression = builder.build();

        assertEquals(expression.evaluate(), new BigDecimal(-10));
    }

    @Test
    public void testAstUnaryBinaryOp() {
        // - - 2 - - 3 4
        PrefixNotationAstBuilder builder = new PrefixNotationAstBuilder();
        Env env = new Env();
        env.registerOperator(new NegateOperator());
        env.registerOperator(new SubtractOperator());
        builder.setEnv(env);
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "2"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.OPERATOR, "-"));
        builder.addToken(new Token(TokenType.NUMBER, "3"));
        builder.addToken(new Token(TokenType.NUMBER, "4"));
        Expression expression = builder.build();

        assertEquals(expression.evaluate(), new BigDecimal(-1));
    }

    @Test
    public void testEval() {
        Arithmetic arithmetic = Arithmetic.create(Notation.PREFIX);
        arithmetic.registerOperator(new SubtractOperator());
        arithmetic.registerOperator(new NegateOperator());
        arithmetic.registerOperator(new SumOperator());
        arithmetic.registerOperator(new DivideOperator());
        arithmetic.registerOperator(new MultiplyOperator());

        assertEquals(arithmetic.evaluate("- 10 2"), new BigDecimal(8));
        assertEquals(arithmetic.evaluate("+ 10 2"), new BigDecimal(12));
        assertEquals(arithmetic.evaluate("/ 10 2"), new BigDecimal(5));
        assertEquals(arithmetic.evaluate("* 10 2"), new BigDecimal(20));

        assertEquals(arithmetic.evaluate("* (- 10 2) + 3 4"), new BigDecimal(56));
        assertEquals(arithmetic.evaluate("* (- 10 2) (+ 3 4)"), new BigDecimal(56));
        assertEquals(arithmetic.evaluate("- - - - - 5"), new BigDecimal(-5));
        assertEquals(arithmetic.evaluate("+ (- 4) + 3 (- 7 (* 2 ( + 4 5 ) 8 ) 10)"), new BigDecimal(-148));
    }
}
