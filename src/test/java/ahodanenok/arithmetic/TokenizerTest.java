package ahodanenok.arithmetic;

import static org.junit.Assert.*;
import org.junit.Test;

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
        assertEquals(token, new Token(TokenType.NUMBER, "1"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "+"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.NUMBER, "2"));

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
        assertEquals(token, new Token(TokenType.NUMBER, "1"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.NUMBER, "321"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.NUMBER, "0"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.NUMBER, "22222"));

        assertNull(tokenizer.next());
    }

    @Test
    public void testOperators() {
        Tokenizer tokenizer = new Tokenizer("+ - * / ! @ $ % # ^ & > < ++ -- !!!! ** <<< >>>>> && ^&%#$@@*#");

        Token token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "+"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "-"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "*"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "/"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "!"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "@"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "$"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "%"));
        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "#"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "^"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "&"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, ">"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "<"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "++"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "--"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "!!!!"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "**"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "<<<"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, ">>>>>"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "&&"));

        token = tokenizer.next();
        assertNotNull(token);
        assertEquals(token, new Token(TokenType.OPERATOR, "^&%#$@@*#"));

        assertNull(tokenizer.next());
    }
}
