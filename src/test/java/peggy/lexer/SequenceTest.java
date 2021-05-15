package peggy.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SequenceTest {
    @Test
    void consumeExpression() {
        Sequence sequence = new Sequence("[a-z]+ [ ]* \"=\" [ ]* [0-9]+");
        assertTrue(sequence.match("num = 3", 0));
        assertEquals(7, sequence.length());
        assertEquals(27, sequence.getEnd());
    }

    @Test
    void consumeExpressionWithPositiveLookahead() {
        Sequence sequence = new Sequence("[0-9]+ \" dollars\"=");
        assertTrue(sequence.match("30 dollars", 0));
        assertEquals(2, sequence.length());
        assertEquals(18, sequence.getEnd());
    }

    @Test
    void consumeQuotationMark() {
        Sequence sequence2= new Sequence("\"abc\"");
        assertTrue(sequence2.match("abc", 0));
    }

    @Test
    void consumeSingleToken() {
        Sequence sequence = new Sequence("[a-z]+");
        assertTrue(sequence.match("abc", 0));
        assertEquals(3, sequence.length());
    }
}