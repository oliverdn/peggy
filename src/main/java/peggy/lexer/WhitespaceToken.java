package peggy.lexer;

public class WhitespaceToken implements TokenInterface {
    private final int end;

    private WhitespaceToken(int end) {
        this.end = end;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String getSymbol() {
        return "_string";
    }

    public static TokenInterface getToken(CharSequence text) {
        int end = 0;
        if (text == null || text.length() > 0 && text.charAt(0) == '_') {
            end += 1;
        }

        return end > 0 ?
                new WhitespaceToken(end) :
                null;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public boolean match(String value, int offset) {
        return Character.isWhitespace(value.charAt(offset));
    }
}
