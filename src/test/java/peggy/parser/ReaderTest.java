package peggy.parser;

import org.junit.jupiter.api.Test;
import peggy.lexer.Choice;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    @Test
    void consumeComplexRule() {
        Reader reader = new Reader("Grammar = [a-z]+ \"=\" [0-9]+ / [a-z]+ \"=\" \"\\\"\" [^\"]* \"\\\"\"");
        Choice choice = reader.readLine();
        assertNotNull(choice);
        assertTrue(choice.match("abc=3", 0));
        assertTrue(choice.match("abc=\"3\"", 0));
    }

    @Test
    void readComplexRule() {
        Reader reader = new Reader("Grammar = [a-z]+ \"=\" [0-9]+ / [a-z]+ \"=\" \"\\\"\" [^\"]* \"\\\"\"");
        Choice choice = reader.readLine();
        assertNotNull(choice);
        assertEquals(2, choice.getSequences().size());
        assertEquals(3, choice.getSequences().get(0).getCardinalTokenCount());
        assertEquals(5, choice.getSequences().get(1).getCardinalTokenCount());
    }

    @Test
    void readSimpleRule() {
        Reader reader = new Reader("Grammar = \"abc\"");
        assertNotNull(reader.readLine());
    }

    @Test
    void readLongerRule() {
        Reader reader = new Reader("Grammar = [a-z]+ \"=\" [0-9]+");
        Choice choice = reader.readLine();
        assertNotNull(choice);
        assertEquals(1, choice.getSequences().size());
        assertEquals(3, choice.getSequences().get(0).getCardinalTokenCount());
    }
}