package peggy.lexer;

/**
 * http://en.wikipedia.org/wiki/Parsing_expression_grammar
 * http://en.wikipedia.org/wiki/Lexical_analysis
 * http://www.uraimo.com/imgs/lisp.png
 */
public class Tokenizer extends AbstractTokenizer {
    private int end;
    private Lexeme lexeme;
    private final CardinalToken cardinalToken;

    private Tokenizer() {
        super(null);
        this.cardinalToken = null;
    }

    public Tokenizer(CharSequence pattern) {
        super(pattern);
        this.cardinalToken = super.getCardinalToken();
    }

    public Tokenizer(CardinalToken cardinalToken) {
        super("");
        this.cardinalToken = cardinalToken;
    }

    private Tokenizer(CharSequence pattern, FunctionList functionList) {
        super(pattern);
        this.functionList = functionList;
        this.cardinalToken = super.getCardinalToken();
    }

    public Tokenizer copy() {
        return new Tokenizer(cardinalToken);
    }

    public Tokenizer copy(CharSequence pattern) {
        return new Tokenizer(pattern, functionList);
    }

    @Override
    public CardinalToken getCardinalToken() {
        return cardinalToken;
    }

    public int getEnd() {
        return end;
    }

    /** Lexeme is a sequence of characters in the source program that matches the pattern for a token and is identified by the lexical analyzer as an instance of that token */
    public Lexeme getLexeme() {
        return lexeme;
    }

    private int getMatchedStringOffset(TokenInterface token, Range cardinality, String value, int offset) {
        int next = offset;
        while (next < value.length() && token.match(value, next)) {
            cardinality.next();
            next += token.length();
            if (!cardinality.hasNext()) {
                break;
            }
        }

        return next;
    }

    public static Tokenizer getTokenizer(FunctionList functionList) {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.setTokenFunctions(functionList);

        return tokenizer;
    }

    public boolean match(String value, int offset) {
        if (cardinalToken != null) {
            TokenInterface token = cardinalToken.getToken();
            Range cardinality = cardinalToken.getCardinalSymbol().getCardinality();
            end = cardinalToken.getEnd();
            int next = getMatchedStringOffset(token, cardinality, value, offset);
            if (cardinality.isValid()) {
                boolean isPositiveLookahead = cardinalToken.getCardinalSymbol() == CardinalSymbol.POSITIVE_LOOKAHEAD;
                lexeme = new Lexeme(token.getSymbol(),
                        !isPositiveLookahead ? value.substring(offset, next) : "",
                        !isPositiveLookahead ? next : offset);
                if (token instanceof NodeInterface) {
                    lexeme.setChildren((NodeInterface) token);
                }
                return true;
            }

            return false;
        }

        return false;
    }
}
