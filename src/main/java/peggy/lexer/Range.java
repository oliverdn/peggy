package peggy.lexer;

public class Range {
    private int i;
    private final int max;
    private final int min;

    Range(int min) {
        this(min, 0);
    }

    Range(int min, int max) {
        this.min = min;
        this.max = max;
        this.i = 0;
    }

    public Range copy() {
        return new Range(min, max);
    }

    public boolean hasNext() {
        return max == 0 || i < max;
    }

    public void next() {
        i += 1;
    }

    public boolean isValid() {
        return i >= min && (max == 0 || i <= max);
    }
}
