// Token.java
package calculator;

public class Token {
    public enum Kind {
        // Tipos de Tokens
        IDENTIFIER, NUMBER, TEXT,
        PLUS, MINUS, TIMES, DIVIDE, ASSIGN,
        LPAREN, RPAREN, SEMICOLON, PERIOD,
        KEYWORD_DECLARE, KEYWORD_READ, KEYWORD_WRITE,
        EOF, UNKNOWN
    }

    public Kind kind;
    public String value;
    public double numValue; // Para armazenar o valor numérico
    public int line;
    public int column;

    public Token(Kind kind, String value, int line, int column) {
        this.kind = kind;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public Token(Kind kind, String value, double numValue, int line, int column) {
        this.kind = kind;
        this.value = value;
        this.numValue = numValue;
        this.line = line;
        this.column = column;
    }
}