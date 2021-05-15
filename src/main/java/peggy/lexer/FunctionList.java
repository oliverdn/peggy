package peggy.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionList {
    private final Function<CharSequence, List<Supplier<TokenInterface>>> tokenFunctionProvider;

    public FunctionList() {
        this.tokenFunctionProvider = (pattern) -> List.of(
                () -> StringToken.getToken(pattern),
                () -> CharacterExpressionToken.getToken(pattern)
        );
    }

    public FunctionList(Function<CharSequence, List<Supplier<TokenInterface>>> tokenFunctionProvider) {
        this.tokenFunctionProvider = tokenFunctionProvider;
    }

    public List<Supplier<TokenInterface>> get(CharSequence pattern) {
        return new ArrayList<>(tokenFunctionProvider.apply(pattern));
    }
}
