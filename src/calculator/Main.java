package calculator;

import java.io.StringReader;
import java.util.Scanner; // Importa o Scanner padrão do Java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Usa o Scanner padrão do Java para ler a entrada do console
        Scanner consoleScanner = new Scanner(System.in);
        String entrada;

        System.out.println("Calculadora de Linha de Comando - v1.0");
        System.out.println("Operadores: +, -, *, /, ^. Para sair, digite 'pare'.");

        SymbolTable globalSymbolTable = new SymbolTable();

        while (true) {
            System.out.print("> ");
            entrada = consoleScanner.nextLine();

            if (entrada.equalsIgnoreCase("pare")) {
                break;
            }

            try {
                // Cria um StringReader com a linha de comando
                StringReader stringReader = new StringReader(entrada + ";");

                // Cria uma nova instância do SEU Scanner (calculator.Scanner)
                // que irá ler a string de código fornecida
                calculator.Scanner calculatorScanner = new calculator.Scanner(stringReader);

                // Passa o SEU Scanner e a tabela de símbolos para o Parser
                Parser parser = new Parser(calculatorScanner, globalSymbolTable);

                parser.parseSingleLine();
            } catch (IOException e) {
                System.err.println("Erro de E/S: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("Erro de Execução: " + e.getMessage());
            }
        }

        System.out.println("Comando 'pare' recebido. Encerrando o programa.");
        consoleScanner.close();
    }
}