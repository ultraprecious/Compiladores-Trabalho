import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do console (System.in)
        Scanner scanner = new Scanner(System.in);
        String entrada;

        System.out.println("Programa iniciado. Digite suas linhas de comando.");
        System.out.println("Para sair, digite 'pare'.");

        // Loop principal para processar as entradas
        while (true) {
            // Lê a próxima linha de entrada
            entrada = scanner.nextLine();

            // Verifica se a entrada é a palavra de saída
            if (entrada.equalsIgnoreCase("pare")) {
                break; // Sai do loop
            }

            // AQUI VOCÊ PODE INSERIR A LÓGICA PARA PROCESSAR A ENTRADA
            // Por exemplo, você pode imprimir a entrada para testar
            System.out.println("Entrada recebida: " + entrada);
        }

        System.out.println("Comando 'pare' recebido. Encerrando o programa.");
        // Fecha o Scanner para liberar os recursos
        scanner.close();
    }
}