package peggy.lexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SequenceMatch {
    private final List<Lexeme> lexemes;

    SequenceMatch() {
        lexemes = new ArrayList<>();
    }

    public void add(Lexeme lexeme) {
        lexemes.add(lexeme);
    }

    public List<Lexeme> find() {
        return Collections.unmodifiableList(lexemes);
    }

    public int length() {
        return lexemes.stream().mapToInt(Lexeme::length).sum();
    }
}
