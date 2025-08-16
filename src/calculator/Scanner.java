package calculator;

import java.io.Reader;
import java.io.IOException;

public class Scanner {
    private Reader reader;
    private char currentCh;
    private int line = 1;
    private int column = 0;

    public Scanner(Reader reader) throws IOException {
        this.reader = reader;
        nextChar();
    }

    private void nextChar() throws IOException {
        int ch = reader.read();
        if (ch == -1) {
            currentCh = (char) 0; // Fim do arquivo
        } else {
            currentCh = (char) ch;
            column++;
            if (currentCh == '\n') {
                line++;
                column = 0;
            }
        }
    }

    private void skipWhitespace() throws IOException {
        while (Character.isWhitespace(currentCh) && currentCh != 0) {
            nextChar();
        }
    }

    public Token nextToken() throws IOException {
        skipWhitespace();

        if (currentCh == 0) {
            return new Token(Token.Kind.EOF, "EOF", line, column);
        }

        if (Character.isLetter(currentCh)) {
            return readIdentifierOrKeyword();
        }

        if (Character.isDigit(currentCh)) {
            return readNumber();
        }

        switch (currentCh) {
            case '+': nextChar(); return new Token(Token.Kind.PLUS, "+", line, column);
            case '-': nextChar(); return new Token(Token.Kind.MINUS, "-", line, column);
            case '*': nextChar(); return new Token(Token.Kind.TIMES, "*", line, column);
            case '/': nextChar(); return new Token(Token.Kind.DIVIDE, "/", line, column);
            case '(': nextChar(); return new Token(Token.Kind.LPAREN, "(", line, column);
            case ')': nextChar(); return new Token(Token.Kind.RPAREN, ")", line, column);
            case ';': nextChar(); return new Token(Token.Kind.SEMICOLON, ";", line, column);
            // NOVO: Adicionado tratamento para o operador de atribuição ':=
            case ':':
                nextChar();
                if (currentCh == '=') {
                    nextChar();
                    return new Token(Token.Kind.ASSIGN, ":=", line, column);
                } else {
                    throw new RuntimeException("Caractere ':' esperado ser seguido por '=', mas encontrou '" + currentCh + "'");
                }
            case '"':
                nextChar();
                StringBuilder text = new StringBuilder();
                while (currentCh != '"' && currentCh != 0) {
                    text.append(currentCh);
                    nextChar();
                }
                if (currentCh == '"') {
                    nextChar();
                    return new Token(Token.Kind.TEXT, text.toString(), line, column);
                } else {
                    throw new RuntimeException("Texto literal não fechado.");
                }
            default:
                throw new RuntimeException("Caractere inválido: '" + currentCh + "' na linha " + line + ", coluna " + column);
        }
    }

    private Token readIdentifierOrKeyword() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (Character.isLetterOrDigit(currentCh) && currentCh != 0) {
            sb.append(currentCh);
            nextChar();
        }
        String id = sb.toString();

        switch (id) {
            case "leia": return new Token(Token.Kind.KEYWORD_READ, id, line, column);
            case "escreva": return new Token(Token.Kind.KEYWORD_WRITE, id, line, column);
            case "declare": return new Token(Token.Kind.KEYWORD_DECLARE, id, line, column);
            default: return new Token(Token.Kind.IDENTIFIER, id, line, column);
        }
    }

    private Token readNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(currentCh) && currentCh != 0) {
            sb.append(currentCh);
            nextChar();
        }
        if (currentCh == '.') {
            sb.append(currentCh);
            nextChar();
            while (Character.isDigit(currentCh) && currentCh != 0) {
                sb.append(currentCh);
                nextChar();
            }
        }
        String numStr = sb.toString();
        return new Token(Token.Kind.NUMBER, numStr, Double.parseDouble(numStr), line, column);
    }
}