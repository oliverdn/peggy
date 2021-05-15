package peggy.lexer;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import static peggy.lexer.CardinalSymbol.*;

abstract class AbstractTokenizer {
    private final CharSequence pattern;
    protected FunctionList functionList;
    private List<Supplier<TokenInterface>> tokenFunctions;

    protected AbstractTokenizer(CharSequence pattern) {
        this.pattern = pattern;
    }

    private <T> T coalesce(List<Supplier<T>> list) {
        for (Supplier<T> sup : list) {
            if (sup != null) {
                T rule = sup.get();
                if (rule != null) {
                    return rule;
                }
            }
        }

        return null;
    }

    private CardinalSymbol getCardinalSymbol(TokenInterface token) {
        List<Supplier<CardinalSymbol>> cardinalities = List.of(
                () -> ONE_OR_MORE.startsWith(pattern, token.getEnd()) ? ONE_OR_MORE : null,
                () -> ONE_OR_NOTHING.startsWith(pattern, token.getEnd()) ? ONE_OR_NOTHING : null,
                () -> POSITIVE_LOOKAHEAD.startsWith(pattern, token.getEnd()) ? POSITIVE_LOOKAHEAD : null,
                () -> ZERO_OR_MORE.startsWith(pattern, token.getEnd()) ? ZERO_OR_MORE : null,
                () -> ONCE
        );

        return Objects.requireNonNull(coalesce(cardinalities));
    }

    public CardinalToken getCardinalToken() {
        TokenInterface token = getToken();

        return token != null ?
                new CardinalToken(token, getCardinalSymbol(token)) :
                null;
    }

    private TokenInterface getToken() {
        if (tokenFunctions == null) {
            tokenFunctions = functionList == null ?
                    new FunctionList().get(pattern) :
                    functionList.get(pattern);
        }

        return coalesce(tokenFunctions);
    }

    protected void setTokenFunctions(FunctionList functionList) {
        this.functionList = functionList;
        if (pattern != null) {
            this.tokenFunctions = functionList.get(pattern);
        }
    }

    protected void setTokenFunctions(List<Supplier<TokenInterface>> tokenFunctions) {
        this.tokenFunctions = tokenFunctions;
    }
}
