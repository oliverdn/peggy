package peggy.vm;

import peggy.lexer.Choice;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Environment<T> {
    private final LinkedHashMap<CharSequence, T> symbols;

    public Environment() {
        this.symbols = new LinkedHashMap<>();
    }

    public boolean contains(CharSequence nonterminalSymbol) {
        return symbols.containsKey(nonterminalSymbol.toString());
    }

    public T first() {
        Iterator<Map.Entry<CharSequence, T>> it = symbols.entrySet().iterator();
        return it.next().getValue();
    }

    public T get(CharSequence nonterminalSymbol) {
        T c = symbols.get(nonterminalSymbol.toString());
        return c;
    }

    public void put(CharSequence name, T value) {
        symbols.put(name.toString(), value);
    }
}
