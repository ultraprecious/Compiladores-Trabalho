package calculator;

import java.io.IOException;

public class Parser {
    private Scanner scanner;
    private Token currentToken;
    private SymbolTable symbolTable;
    private Token la; // Lookahead token

    public Parser(Scanner scanner, SymbolTable symbolTable) throws IOException {
        this.scanner = scanner;
        this.symbolTable = symbolTable;
        this.la = scanner.nextToken();
        this.currentToken = new Token(Token.Kind.EOF, "", -1, -1);
        scan();
    }

    public void parseSingleLine() throws IOException {
        command();
    }


    private void scan() throws IOException {
        currentToken = la;
        la = scanner.nextToken();
    }


    private void expect(Token.Kind kind) throws IOException {
        if (currentToken.kind == kind) {
            scan();
        } else {
            error("Esperado " + kind + ", mas encontrado " + currentToken.kind);
        }
    }

    private void command() throws IOException {
        if (currentToken.kind == Token.Kind.KEYWORD_DECLARE) {
            declareCommand();
        } else if (currentToken.kind == Token.Kind.KEYWORD_READ) {
            readCommand();
        } else if (currentToken.kind == Token.Kind.KEYWORD_WRITE) {
            writeCommand();
        } else {
            // Verifica se é uma atribuição usando o tipo do token atual e o do lookahead (la.kind)
            if (currentToken.kind == Token.Kind.IDENTIFIER && la.kind == Token.Kind.ASSIGN) {
                assignCommand();
            }

            else {
                double result = expression();
                System.out.println("Resultado da expressão: " + result);
                expect(Token.Kind.SEMICOLON);
            }
        }
    }

    private void declareCommand() throws IOException {
        expect(Token.Kind.KEYWORD_DECLARE);
        Token varName = currentToken;
        expect(Token.Kind.IDENTIFIER);
        symbolTable.declareVariable(varName.value);
        System.out.println("Variável '" + varName.value + "' declarada.");
        expect(Token.Kind.SEMICOLON);
    }

    private void assignCommand() throws IOException {
        Token varName = currentToken;
        expect(Token.Kind.IDENTIFIER);
        expect(Token.Kind.ASSIGN);
        double result = expression();
        symbolTable.assignVariable(varName.value, result);
        System.out.println("Atribuição: " + varName.value + " = " + result);
        expect(Token.Kind.SEMICOLON);
    }

    private void readCommand() throws IOException {
        expect(Token.Kind.KEYWORD_READ);
        expect(Token.Kind.LPAREN);
        if (currentToken.kind != Token.Kind.IDENTIFIER) {
            error("Esperado identificador após 'leia('.");
        }
        System.out.println("Comando 'leia' reconhecido para a variável '" + currentToken.value + "'.");
        scan();
        expect(Token.Kind.RPAREN);
        expect(Token.Kind.SEMICOLON);
    }

    private void writeCommand() throws IOException {
        expect(Token.Kind.KEYWORD_WRITE);
        expect(Token.Kind.LPAREN);
        if (currentToken.kind == Token.Kind.TEXT) {
            System.out.println("Saída: " + currentToken.value);
            scan();
        } else if (currentToken.kind == Token.Kind.IDENTIFIER) {
            double value = symbolTable.getVariableValue(currentToken.value);
            System.out.println("Saída: " + value);
            scan();
        } else {
            error("Esperado texto ou identificador após 'escreva('.");
        }
        expect(Token.Kind.RPAREN);
        expect(Token.Kind.SEMICOLON);
    }

    private double expression() throws IOException {
        double result = term();
        while (currentToken.kind == Token.Kind.PLUS || currentToken.kind == Token.Kind.MINUS) {
            Token op = currentToken;
            scan();
            double right = term();
            if (op.kind == Token.Kind.PLUS) {
                result += right;
            } else {
                result -= right;
            }
        }
        return result;
    }

    private double term() throws IOException {
        double result = power();
        while (currentToken.kind == Token.Kind.TIMES || currentToken.kind == Token.Kind.DIVIDE) {
            Token op = currentToken;
            scan();
            double right = power();
            if (op.kind == Token.Kind.TIMES) {
                result *= right;
            } else {
                if (right == 0) {
                    throw new RuntimeException("Erro: Divisão por zero.");
                }
                result /= right;
            }
        }
        return result;
    }

    private double power() throws IOException {
        double result = factor();
        if (currentToken.kind == Token.Kind.POWER) {
            scan();
            double right = power();
            result = Math.pow(result, right);
        }
        return result;
    }

    private double factor() throws IOException {
        if (currentToken.kind == Token.Kind.NUMBER) {
            double value = currentToken.numValue;
            scan();
            return value;
        } else if (currentToken.kind == Token.Kind.IDENTIFIER) {
            if (!symbolTable.exists(currentToken.value)) {
                throw new RuntimeException("Erro: Variável '" + currentToken.value + "' não declarada.");
            }
            double value = symbolTable.getVariableValue(currentToken.value);
            scan();
            return value;
        } else if (currentToken.kind == Token.Kind.LPAREN) {
            scan();
            double result = expression();
            expect(Token.Kind.RPAREN);
            return result;
        } else {
            error("Esperado número, identificador ou parêntese.");
            return 0;
        }
    }

    private void error(String message) {
        System.err.println("Erro na linha " + currentToken.line + ", coluna " + currentToken.column + ": " + message);
        throw new RuntimeException(message);
    }
}