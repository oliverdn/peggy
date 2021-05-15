package peggy.lexer;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final List<CardinalToken> cardinalTokens;
    private int end;
    private SequenceMatch sequenceMatch;

    public Sequence(String pattern) {
        this.end = 0;
        this.cardinalTokens = new ArrayList<>();

        TokenInterface ws = WhitespaceToken.getToken(null);
        Tokenizer tokenizer = new Tokenizer(pattern);
        CardinalToken cardinalToken = tokenizer.getCardinalToken();
        while (cardinalToken != null) {
            this.cardinalTokens.add(cardinalToken);
            this.end += cardinalToken.getEnd();

            while (this.end < pattern.length() &&
                    ws != null &&
                    ws.match(pattern, this.end)) {
                this.end += ws.length();
            }

            cardinalToken = new Tokenizer(pattern.substring(this.end)).getCardinalToken();
        }
    }

    public Sequence() {
        this.cardinalTokens = new ArrayList<>();
        this.end = 0;
    }

    public void addCardinalToken(CardinalToken cardinalToken) {
        cardinalTokens.add(cardinalToken);
        end += cardinalToken.getEnd();
    }

    public Sequence copy() {
        Sequence sequence = new Sequence();
        sequence.cardinalTokens.addAll(cardinalTokens);
        return sequence;
    }

    public int getCardinalTokenCount() {
        return cardinalTokens.size();
    }

    public int getEnd() {
        return end;
    }

    protected SequenceMatch getSequenceMatch() {
        return sequenceMatch;
    }

    public int length() {
        return sequenceMatch == null ? 0 : sequenceMatch.length();
    }

    public boolean match(String value, int offset) {
        sequenceMatch = new SequenceMatch();

        int next = offset;
        for (CardinalToken cardinalToken : cardinalTokens) {
            Tokenizer tokenizer = new Tokenizer(cardinalToken);
            if (tokenizer.match(value, next)) {
                sequenceMatch.add(tokenizer.getLexeme());
                next = tokenizer.getLexeme().getEnd();
            } else {
                sequenceMatch = null;
                return false;
            }
        }

        return true;
    }
}
