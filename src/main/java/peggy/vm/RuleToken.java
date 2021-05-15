package peggy.vm;

import peggy.lexer.*;

import java.util.List;

public class RuleToken implements NodeInterface, TokenInterface {
    private final int end;
    private final Environment<Choice> environment;
    private SequenceMatch sequenceMatch;
    private final CharSequence symbol;

    private RuleToken(Environment<Choice> environment, CharSequence nonterminalSymbol, int end) {
        this.end = end;
        this.environment = environment;
        this.symbol = nonterminalSymbol;
    }

    @Override
    public List<Lexeme> childLexemes() {
        return sequenceMatch == null ?
                List.of() :
                sequenceMatch.find();
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public String getSymbol() {
        return symbol.toString();
    }

    public static TokenInterface getToken(Environment<Choice> environment, CharSequence text) {
        int end = 0;
        while (Character.isLetter(text.charAt(end))) {
            end += 1;
        }

        return end > 0 ?
                new RuleToken(environment, text.subSequence(0, end), end) :
                null;
    }

    @Override
    public int length() {
        return sequenceMatch.length();
    }

    @Override
    public boolean match(String value, int offset) {
        Choice choice = environment.get(symbol);
        if (choice.match(value, offset)) {
            sequenceMatch = choice.getSequenceMatch();
            return true;
        }

        return false;
    }
}
