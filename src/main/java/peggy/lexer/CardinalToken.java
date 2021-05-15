package peggy.lexer;

public class CardinalToken {
    private final CardinalSymbol cardinalSymbol;
    private final TokenInterface token;

    public CardinalToken(TokenInterface token, CardinalSymbol cardinalSymbol) {
        this.token = token;
        this.cardinalSymbol = cardinalSymbol;
    }

    public CardinalSymbol getCardinalSymbol() {
        return cardinalSymbol;
    }

    public int getEnd() {
        return token.getEnd() + cardinalSymbol.length();
    }

    public TokenInterface getToken() {
        return token;
    }
}
