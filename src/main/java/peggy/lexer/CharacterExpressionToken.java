package peggy.lexer;

import java.util.Locale;

public class CharacterExpressionToken implements TokenInterface {
    private final int end;
    private final String lcpattern;
    private final boolean negated;
    private final String ucpattern;

    private CharacterExpressionToken(String pattern, boolean negated, int end) {
        this.negated = negated;
        int start = negated ? 2 : 1;
        this.end = end;

        lcpattern = end > 0 ?
                pattern.toLowerCase(Locale.ROOT).substring(start, end - 1) :
                null;
        ucpattern = end > 0 ?
                pattern.toUpperCase(Locale.ROOT).substring(start, end - 1) :
                null;
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

        boolean negated = text.length() > 2 && text.charAt(1) == '^';

        if (text.length() > 0 && text.charAt(0) == '[') {
            while (++end < text.length() && text.charAt(end) != ']') {
                if (text.charAt(end) == '\\') {
                    end += 1;
                }
            }
        }

        return end > 0 ?
                new CharacterExpressionToken(text.toString(), negated, end + 1) :
                null;
    }

    public boolean isNegated() {
        return negated;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public boolean match(String value, int offset) {
        int code = Character.codePointAt(value, offset);

        if (lcpattern != null && ucpattern != null) {
            boolean foundAny = false;
            int i = 0;
            for (; i < lcpattern.length() && !foundAny; i++) {
                if (lcpattern.charAt(i) == '\\') {
                    if (i + 1 < lcpattern.length()) {
                        int next = Characters.unescapedMatchedLength(lcpattern, code, i);
                        foundAny = negated != (next > 0);
                        i += Characters.unescapedLength(lcpattern, i) - 1;
                    } else {
                        i += 1;
                    }
                } else if (i + 2 < lcpattern.length() && lcpattern.charAt(i + 1) == '-') {
                    boolean found = Character.codePointAt(lcpattern, i) <= code &&
                            Character.codePointAt(lcpattern, i + 2) >= code ||
                            Character.codePointAt(ucpattern, i) <= code &&
                                    Character.codePointAt(ucpattern, i + 2) >= code;
                    foundAny = negated != found; // found && !negated || !found && negated;
                    i += 2;
                } else if (i < lcpattern.length()) {
                    boolean found = Character.codePointAt(lcpattern, i) == code ||
                            Character.codePointAt(ucpattern, i) == code;
                    foundAny = negated != found;
                }
            }

            return foundAny;
        }

        return false;
    }
}
