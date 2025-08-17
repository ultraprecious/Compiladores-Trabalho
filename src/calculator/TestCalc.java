package calculator;

import java.io.StringReader;
import java.io.IOException;

public class TestCalc {
    public static void main(String[] args) {
        String code =
                "declare a; " +
                        "a := 10.5; " +
                        "declare b; " +
                        "b := a * 2 + 5; " +
                        "escreva(\"Valor de a\");" +
                        "escreva(a);" +
                        "escreva(\"Valor de b\");" +
                        "escreva(b);" +
                        "leia(a);" +
                        "b := (a + 5) ^ 2;" +
                        "escreva(\"Novo valor de b\");" +
                        "escreva(b);";

        try {
            System.out.println("Código-fonte a ser analisado:\n" + code + "\n---");
            Scanner scanner = new Scanner(new StringReader(code));

            SymbolTable symbolTable = new SymbolTable();

            Parser parser = new Parser(scanner, symbolTable);

            // NOVO: Chamada para o método correto
            parser.parseSingleLine();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
        }
    }
}