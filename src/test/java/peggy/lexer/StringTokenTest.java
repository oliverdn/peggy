package peggy.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTokenTest {
    @Test
    void consumeShortString() {
        TokenInterface token = StringToken.getToken("\"abc\"");
        assertNotNull(token);
        assertTrue(token.match("abcd", 0));
    }

    @Test
    void consumeQuotationMark() {
        TokenInterface token = StringToken.getToken("\"\\\"\"");
        assertNotNull(token);
        assertTrue(token.match("\"",0));
    }

    @Test
    void consumeStringByOffset() {
        TokenInterface token = StringToken.getToken("\"bc\"");
        assertNotNull(token);
        assertTrue(token.match("abcd", 1));
    }

    @Test
    void consumeWhitespaceCharacter() {
        TokenInterface token = StringToken.getToken("\"\\t\"");
        assertNotNull(token);
        assertTrue(token.match("\t",0));
    }

    @Test
    void discardShortString() {
        TokenInterface token = StringToken.getToken("\"ac\"");
        assertNotNull(token);
        assertFalse(token.match("abcd", 0));
    }

    @Test
    void expressionLength() {
            TokenInterface token = StringToken.getToken("\"abc\"");
        assertNotNull(token);
        assertEquals(5, token.getEnd());
    }
}