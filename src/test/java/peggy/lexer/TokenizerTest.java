package peggy.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void consumeSingleCharacter() {
        Tokenizer tokenizer = new Tokenizer("[a-z]");
        assertTrue(tokenizer.match("abc", 0));
        assertEquals(1, tokenizer.getLexeme().length());
        assertEquals(5, tokenizer.getEnd());
    }

    @Test
    void consumeOneOrMore() {
        Tokenizer tokenizer = new Tokenizer("[a-z]+");
        assertTrue(tokenizer.match("abc", 0));
        assertEquals(3, tokenizer.getLexeme().length());
    }

    @Test
    void consumeOneOrNothing() {
        Tokenizer tokenizer = new Tokenizer("[a-z]?");
        assertTrue(tokenizer.match("abc", 0));
        assertEquals(1, tokenizer.getLexeme().length());
    }

    @Test
    void consumeEmpty() {
        Tokenizer tokenizer = new Tokenizer("[a-z]*");
        assertTrue(tokenizer.match("", 0));
        tokenizer = new Tokenizer("[a-z]?");
        assertTrue(tokenizer.match("", 0));
    }

    @Test
    void consumeZeroOrMore() {
        Tokenizer tokenizer = new Tokenizer("[a-z]*");
        assertTrue(tokenizer.match("abc", 0));
        assertEquals(3, tokenizer.getLexeme().length());
    }

    @Test
    void discardEmpty() {
        Tokenizer tokenizer = new Tokenizer("[a-z]+");
        assertFalse(tokenizer.match("", 0));
        tokenizer = new Tokenizer("[a-z]");
        assertFalse(tokenizer.match("", 0));
    }

    @Test
    void discardPositiveLookahead() {
        Tokenizer tokenizer = new Tokenizer("[a-z]=");
        assertTrue(tokenizer.match("abc", 0));
        assertEquals(0, tokenizer.getLexeme().getEnd());
    }
}