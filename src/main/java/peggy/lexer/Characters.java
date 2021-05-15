package peggy.lexer;

public class Characters {
    protected static int unescapedLength(CharSequence text, int offset) {
        int i = offset;
        char ch = text.charAt(i);
        if (ch == '\\') {
            char nextChar = text.charAt(i + 1);
            switch (nextChar) {
                case '\\':
                    ch = '\\';
                    break;
                case 'b':
                    ch = '\b';
                    break;
                case 'f':
                    ch = '\f';
                    break;
                case 'n':
                    ch = '\n';
                    break;
                case 'r':
                    ch = '\r';
                    break;
                case 't':
                    ch = '\t';
                    break;
                case '\"':
                    ch = '\"';
                    break;
                case '\'':
                    ch = '\'';
                    break;
                case 'u':
                    if (i >= text.length() - 5) {
                        ch = 'u';
                        break;
                    }
                    int unicode = Integer.parseInt(
                            "" + text.charAt(i + 2) + text.charAt(i + 3)
                                    + text.charAt(i + 4) + text.charAt(i + 5), 16);
                    i += 5;
                    return 6;
            }
            i += 1;
            return 2;
        }
        return 0;
    }

    protected static int unescapedMatchedLength(CharSequence text, int code, int offset) {
        int i = offset;
        char ch = text.charAt(i);
        if (ch == '\\') {
            char nextChar = text.charAt(i + 1);
            switch (nextChar) {
                case '\\':
                    ch = '\\';
                    break;
                case 'b':
                    ch = '\b';
                    break;
                case 'f':
                    ch = '\f';
                    break;
                case 'n':
                    ch = '\n';
                    break;
                case 'r':
                    ch = '\r';
                    break;
                case 't':
                    ch = '\t';
                    break;
                case '\"':
                    ch = '\"';
                    break;
                case '\'':
                    ch = '\'';
                    break;
                case 'u':
                    if (i >= text.length() - 5) {
                        ch = 'u';
                        break;
                    }
                    int unicode = Integer.parseInt(
                            "" + text.charAt(i + 2) + text.charAt(i + 3)
                            + text.charAt(i + 4) + text.charAt(i + 5), 16);
                    i += 5;
                    return unicode == code ? 6 : 0;
                default:
                    char lc = Character.toLowerCase(nextChar);
                    char uc = Character.toUpperCase(nextChar);
                    return lc == code || uc == code ? 2 : 0;

            }
            i += 1;
            return ch == code ? 2 : 0;
        }
        return Character.codePointAt(text, i) == code ? 1 : 0;
    }
}
