package peggy.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhitespaceTokenTest {
    private TokenInterface token;

    @BeforeEach
    void init() {
        token = WhitespaceToken.getToken("_");
    }

    @Test
    void consumeSpace() {
        assertTrue(token.match(" ", 0));
    }

    @Test
    void discardLetter() {
        assertFalse(token.match("a", 0));
    }

    @Test
    void expressionLength() {
        assertEquals(1, token.getEnd());
    }
}