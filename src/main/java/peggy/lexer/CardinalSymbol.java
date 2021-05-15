package peggy.lexer;

public enum CardinalSymbol {
    ONCE("", new Range(1, 1)),
    ONE_OR_MORE("+", new Range(1)),
    ONE_OR_NOTHING("?", new Range(0, 1)),
    ZERO_OR_MORE("*", new Range(0)),
    POSITIVE_LOOKAHEAD("=", new Range(1, 1));

    private final Range cardinality;
    private final String text;

    CardinalSymbol(String text, Range cardinality) {
        this.text = text;
        this.cardinality = cardinality;
    }

    public int length() {
        return text.length();
    }

    public Range getCardinality() {
        return cardinality.copy();
    }

    public boolean startsWith(CharSequence pattern, int offset) {
        return text.length() == 0 ||
                offset < pattern.length() &&
                text.charAt(0) == pattern.charAt(offset);
    }
}
