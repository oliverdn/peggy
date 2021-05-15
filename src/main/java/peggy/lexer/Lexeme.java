package peggy.lexer;

import java.util.List;

public class Lexeme {
    private List<Lexeme> children;
    private final int end;
    private boolean negated;
    private final String symbol;
    private final String value;

    public Lexeme(String nonterminalSymbol, String value, int end) {
        this.end = end;
        this.symbol = nonterminalSymbol;
        this.value = value;
    }

    public List<Lexeme> childLexemes() {
        return children;
    }

    public int getEnd() {
        return end;
    }

    public String getSymbol() {
        return symbol;
    }

    public int length() {
        return value.length();
    }

    boolean isNegated() {
        return negated;
    }

    protected void setChildren(NodeInterface node) {
        children = node.childLexemes();
    }

    public String toExpression() {
        StringBuilder builder = new StringBuilder();
        builder.append("(:");
        builder.append(symbol);
        builder.append(" ");
        builder.append("\"" + value + "\"");
        if (children != null &&
                (children.size() != 1 || !children.get(0).getSymbol().equals("_string"))) {
            for (Lexeme child : children) {
                builder.append(" ");
                builder.append(child.toExpression());
            }
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
