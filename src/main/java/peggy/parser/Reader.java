package peggy.parser;

import peggy.lexer.*;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    private final Tokenizer assignmentTokenizer;
    private final Tokenizer choiceTokenizer;
    private final Tokenizer expressionTokenizer;
    private int end;
    private final Tokenizer symbolTokenizer;
    private final String text;
    private final Tokenizer whitespaceTokenizer;

    public Reader(String text) {
        this(text, new FunctionList());
    }

    public Reader(String text, FunctionList functionList) {
        this.end = 0;
        this.text = text;
        this.assignmentTokenizer = new Tokenizer("\"=\"");
        this.choiceTokenizer = new Tokenizer("\"/\"");
        this.expressionTokenizer = Tokenizer.getTokenizer(functionList);
        this.symbolTokenizer = new Tokenizer("[a-z]+");
        this.whitespaceTokenizer = new Tokenizer("[ \\t\\r\\n]+");
    }

    public int getEnd() {
        return end;
    }

    private String leftHandSide() {
        String name = null;
        List<Tokenizer> tokenizers = List.of(symbolTokenizer.copy(),
                whitespaceTokenizer.copy(),
                assignmentTokenizer.copy(),
                whitespaceTokenizer.copy());
        for (Tokenizer tokenizer : tokenizers) {
            if (tokenizer.match(text, end)) {
                if (name == null) {
                    name = tokenizer.getLexeme().toString();
                }
                end += tokenizer.getLexeme().length();
            } else {
                return null;
            }
        }

        return name;
    }

    public Choice readLine() {
        String name = leftHandSide();
        if (name == null) {
            return null;
        }

        return new Choice(name, rightHandSide());
    }

    public List<Sequence> rightHandSide() {
        Sequence sequence = new Sequence();
        List<Sequence> sequences = new ArrayList<>(
                Arrays.asList(sequence));
        CardinalToken cardinalToken = expressionTokenizer.copy(CharBuffer.wrap(text)
                .subSequence(end, text.length()))
                .getCardinalToken();
        while (cardinalToken != null) {
            // match TokenInterface
            sequence.addCardinalToken(cardinalToken);
            end += cardinalToken.getEnd();
            // match whitespace
            Tokenizer tokenizer = whitespaceTokenizer.copy();
            if (tokenizer.match(text, end)) {
                end += tokenizer.getLexeme().length();
            }
            // match "/" character
            Tokenizer separator = choiceTokenizer.copy();
            if (separator.match(text, end)) {
                sequence = new Sequence();
                sequences.add(sequence);
                end += separator.getLexeme().length();
            }
            // match whitespace
            if (tokenizer.match(text, end)) {
                end += tokenizer.getLexeme().length();
            }

            cardinalToken = expressionTokenizer.copy(CharBuffer.wrap(text)
                    .subSequence(end, text.length()))
                    .getCardinalToken();
        }

        return sequences;
    }

    public boolean seekAfterIndexOf(char ch) {
        int i = text.indexOf(ch, end);
        if (i > -1) {
            end = i + 1;
            return true;
        }

        return false;
    }
}
