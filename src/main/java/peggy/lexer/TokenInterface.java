package peggy.lexer;

public interface TokenInterface {
    int getEnd();

    String getSymbol();

    /** Length of matched value. */
    int length();

    boolean match(String value, int offset);
}
