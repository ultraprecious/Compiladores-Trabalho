package calculator;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Double> variables;

    public SymbolTable() {
        this.variables = new HashMap<>();
    }

    // Declara uma variável (aqui, apenas a coloca no mapa com valor 0.0)
    public void declareVariable(String name) {
        if (!variables.containsKey(name)) {
            variables.put(name, 0.0);
        } else {
            throw new RuntimeException("Variável '" + name + "' já foi declarada.");
        }
    }

    // Atribui um valor a uma variável
    public void assignVariable(String name, double value) {
        if (variables.containsKey(name)) {
            variables.put(name, value);
        } else {
            throw new RuntimeException("Variável '" + name + "' não foi declarada.");
        }
    }

    public double getVariableValue(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            // Em um compilador real, isso seria um erro semântico
            throw new RuntimeException("Variável '" + name + "' não foi declarada.");
        }
    }

    public boolean exists(String name) {
        return variables.containsKey(name);
    }
}