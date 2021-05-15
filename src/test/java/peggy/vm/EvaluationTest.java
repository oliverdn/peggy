package peggy.vm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import peggy.lexer.SequenceMatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationTest {
    private Evaluation evaluation;

    @BeforeEach
    void init() {
        evaluation = new Evaluation();
    }

    @Test
    void consumeGrammar() throws IOException {
        evaluation.load(Files.readString(Paths.get("src/test/resources/japanese_simplified.peg")));
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/japanese_test.txt", StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            while (line != null) {
                assertTrue(evaluation.match(line, 0));
                SequenceMatch sequenceMatch = evaluation.getSequenceMatch();
                assertTrue(sequenceMatch.length() > 0);
                String expression = evaluation.getSequenceMatch()
                        .find()
                        .get(0)
                        .toExpression();
                assertTrue(expression.length() > 0);
                line = reader.readLine();
            }
        }
    }

    @Test
    void consumeReference() {
        evaluation.load("Grammar = Rule \"=\" Value ;;\n" +
                "Rule = [a-z]+ ;;\n" +
                "Value = [0-9]+ ;;");
        assertTrue(evaluation.match("abc=3", 0));
        assertEquals(3, evaluation.getEnvironment().get("Grammar")
                .getSequences().get(0).getCardinalTokenCount());
        SequenceMatch sequenceMatch = evaluation.getSequenceMatch();
        assertEquals(5, sequenceMatch.length());
    }
}