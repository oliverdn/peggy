package peggy.lexer;

public class StringToken implements TokenInterface {
    private final int end;
    private final CharSequence text;
    private final int unescapedLength;

    private StringToken(CharSequence text, int end) {
        this.end = end;
        this.text = text;

        int i = 0;
        int j = 0;
        for (; i < text.length(); i++, j++) {
            if (text.charAt(i) == '\\') {
                int next = Characters.unescapedLength(text, i);
                i += next - 1;
            }
        }
        this.unescapedLength = j;
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
        if (text.length() > 0 && text.charAt(0) == '"') {
            while (++end < text.length() && text.charAt(end) != '"') {
                if (text.charAt(end) == '\\') {
                    end += 1;
                }
            }
        }

        return end > 0 ?
                new StringToken(text.subSequence(1, end), end + 1) :
                null;
    }

    @Override
    public int length() {
        return unescapedLength;
    }

    @Override
    public boolean match(String value, int offset) {
        int i = 0;
        for (int j = offset; i < text.length(); i++, j++) {
            if (offset + i >= value.length()) {
                return false;
            } else if (text.charAt(i) == '\\') {
                int code = Character.codePointAt(value, j);
                int next = Characters.unescapedMatchedLength(text, code, i);
                if (next == 0) {
                    return false;
                } else {
                    i += next - 1;
                }
            } else if (text.charAt(i) != value.charAt(j)) {
                return false;
            }
        }

        return true;
    }
}
