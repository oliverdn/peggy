package peggy.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterExpressionTokenTest {
    @Test
    void consumeCaseInsensitiveCharacter() {
        TokenInterface token = CharacterExpressionToken.getToken("[a-z]");
        assertNotNull(token);
        assertTrue(token.match("a", 0));
        assertTrue(token.match("A", 0));
    }

    @Test
    void consumeNegatedExpression() {
        TokenInterface token = CharacterExpressionToken.getToken("[^a-z]");
        assertNotNull(token);
        assertFalse(token.match("a", 0));
        assertTrue(token.match("0", 0));
    }

    @Test
    void consumeSingleCharacter() {
        TokenInterface token = CharacterExpressionToken.getToken("[a-z ]");
        assertNotNull(token);
        assertTrue(token.match("a", 0));
        assertTrue(token.match("z", 0));
        assertTrue(token.match(" ", 0));
    }

    @Test
    void consumeWhitespaceCharacter() {
        TokenInterface tabToken = CharacterExpressionToken.getToken("[\\t]");
        assertNotNull(tabToken);
        assertTrue(tabToken.match("\t", 0));
        assertFalse(tabToken.match("\n", 0));
        TokenInterface token = CharacterExpressionToken.getToken("[\\t\\n]");
        assertNotNull(token);
        assertTrue(token.match("\n", 0));
    }

    @Test
    void discardEscapedCharacter() {
        TokenInterface tabToken = CharacterExpressionToken.getToken("[ \\t\\r\\n\\a]");
        assertNotNull(tabToken);
        assertTrue(tabToken.match("A", 0));
        assertFalse(tabToken.match("R", 0));
    }

    @Test
    void discardNumber() {
        TokenInterface token = CharacterExpressionToken.getToken("[a-z ]");
        assertNotNull(token);
        assertFalse(token.match("0", 0));
    }

    @Test
    void expressionLength() {
        TokenInterface token = CharacterExpressionToken.getToken("[a-z]");
        assertNotNull(token);
        assertEquals(5, token.getEnd());
    }
}