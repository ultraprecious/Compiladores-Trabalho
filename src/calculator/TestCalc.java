// TestCalc.java
package calculator;

import java.io.StringReader;
import java.io.IOException;

public class TestCalc {
    public static void main(String[] args) {
        String code = "2 * 4 + 3 * 2";


        try {
            // Simulação de entrada do teclado para o 'leia'
            // Em um programa real, o parser esperaria uma entrada do usuário
            System.out.println("Código-fonte a ser analisado:\n" + code + "\n---");
            Scanner scanner = new Scanner(new StringReader(code));
            Parser parser = new Parser(scanner);
            parser.parse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
        }
    }
}