package ahodanenok.arithmetic;

import static org.junit.Assert.*;

import ahodanenok.arithmetic.ast.Expression;
import ahodanenok.arithmetic.ast.NumberExpression;
import ahodanenok.arithmetic.ast.OperatorExpression;
import ahodanenok.arithmetic.operator.NegateOperator;
import ahodanenok.arithmetic.operator.SubtractOperator;
import ahodanenok.arithmetic.operator.SumOperator;
import org.junit.Test;

import java.math.BigDecimal;

public class TokenizerTest {

    @Test
    public void testEmpty() {
        Tokenizer tokenizer = new Tokenizer("");
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
    }

    @Test
    public void testEnd() {
        Tokenizer tokenizer = new Tokenizer(" 1 + 2 ");

        Token token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "1"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "+"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "2"), token);

        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
        assertNull(tokenizer.next());
    }

    @Test
    public void testIntegers() {
        Tokenizer tokenizer = new Tokenizer("1 321 0 22222");

        Token token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "1"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "321"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "0"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "22222"), token);

        assertNull(tokenizer.next());
    }

    @Test
    public void testOperators() {
        Tokenizer tokenizer = new Tokenizer("+ - * / ! @ $ % # ^ & > < ++ -- !!!! ** <<< >>>>> && ^&%#$@@*#");

        Token token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "+"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "-"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "*"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "/"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "!"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "@"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "$"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "%"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "#"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "^"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "&"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, ">"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "<"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "++"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "--"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "!!!!"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "**"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "<<<"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, ">>>>>"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "&&"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.OPERATOR, "^&%#$@@*#"), token);

        assertNull(tokenizer.next());
    }

    @Test
    public void testFunctions() {
        Tokenizer tokenizer = new Tokenizer(" sum sum123 pow(sum(1, 2), div(10, 5)) ");

        Token token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.FUNCTION, "sum"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.FUNCTION, "sum123"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.FUNCTION, "pow"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.LP), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.FUNCTION, "sum"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.LP), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "1"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "2"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.RP), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.FUNCTION, "div"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.LP), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "10"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.NUMBER, "5"), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.RP), token);

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(new Token(TokenType.RP), token);

        assertNull(tokenizer.next());
    }
}
