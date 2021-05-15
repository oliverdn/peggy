package peggy.vm;

import peggy.lexer.*;
import peggy.parser.Reader;

import java.util.List;

public class Evaluation {
    private final Environment<Choice> environment;

    public Evaluation() {
        this.environment = new Environment<>();
    }

    public SequenceMatch getSequenceMatch() {
        return environment.first().getSequenceMatch();
    }

    public Environment<Choice> getEnvironment() {
        return environment;
    }

    public void load(String text) {
        Reader reader = new Reader(text, new FunctionList((pattern) -> List.of(
                () -> StringToken.getToken(pattern),
                () -> CharacterExpressionToken.getToken(pattern),
                () -> RuleToken.getToken(environment, pattern)
        )));
        Choice choice = reader.readLine();
        while (choice != null) {
            environment.put(choice.getSymbol(), choice);
            reader.seekAfterIndexOf('\n');
            choice = reader.readLine();
        }
    }

    public boolean match(String value, int offset) {
        return environment.first().match(value, offset);
    }
}
