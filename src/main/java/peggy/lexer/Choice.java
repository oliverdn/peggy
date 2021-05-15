package peggy.lexer;

import java.util.List;
import java.util.stream.Collectors;

public class Choice {
    private final String symbol;
    private SequenceMatch sequenceMatch;
    private final List<Sequence> sequences;

    public Choice(String nonterminalSymbol, List<Sequence> sequences) {
        this.sequences = sequences;
        this.symbol = nonterminalSymbol;
    }

    public SequenceMatch getSequenceMatch() {
        return sequenceMatch;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean match(String value, int offset) {
        for (Sequence sequence: sequences.stream()
                .map(Sequence::copy)
                .collect(Collectors.toList())) {
            if (sequence.match(value, offset)) {
                sequenceMatch = sequence.getSequenceMatch();
                return true;
            }
        }

        return false;
    }
}
