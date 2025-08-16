// Parser.java
package calculator;

import java.io.IOException;

public class Parser {
    private Scanner scanner;
    private Token currentToken;
    private SymbolTable symbolTable;

    public Parser(Scanner scanner) throws IOException {
        this.scanner = scanner;
        this.symbolTable = new SymbolTable();
        this.currentToken = scanner.nextToken();
    }

    public void parse() throws IOException {
        System.out.println("Iniciando a análise...");
        while (currentToken.kind != Token.Kind.EOF) {
            command();
        }
        System.out.println("Análise concluída com sucesso.");
    }

    private void command() throws IOException {
        // Cmd -> CmdLeitura | CmdEscrita | CmdExpr | CmdAtribuicao
        if (currentToken.kind == Token.Kind.KEYWORD_DECLARE) {
            declareCommand();
        } else if (currentToken.kind == Token.Kind.KEYWORD_READ) {
            readCommand();
        } else if (currentToken.kind == Token.Kind.KEYWORD_WRITE) {
            writeCommand();
        } else if (currentToken.kind == Token.Kind.IDENTIFIER) {
            assignCommand();
        } else {
            // Se não for um comando esperado, tenta tratar como uma expressão
            double result = expression();
            System.out.println("Resultado da expressão: " + result);
            expect(Token.Kind.SEMICOLON);
        }
    }

    private void declareCommand() throws IOException {
        expect(Token.Kind.KEYWORD_DECLARE);
        // NOVO: Armazena o token do identificador ANTES de consumi-lo
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
        expect(Token.Kind.IDENTIFIER);
        System.out.println("Comando 'leia' reconhecido para a variável '" + currentToken.value + "'.");
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
        double result = factor();
        while (currentToken.kind == Token.Kind.TIMES || currentToken.kind == Token.Kind.DIVIDE) {
            Token op = currentToken;
            scan();
            double right = factor();
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

    private void expect(Token.Kind kind) throws IOException {
        if (currentToken.kind == kind) {
            scan();
        } else {
            error("Esperado " + kind + ", mas encontrado " + currentToken.kind);
        }
    }

    private void scan() throws IOException {
        this.currentToken = scanner.nextToken();
    }

    private void error(String message) {
        System.err.println("Erro na linha " + currentToken.line + ", coluna " + currentToken.column + ": " + message);
        throw new RuntimeException(message);
    }
}