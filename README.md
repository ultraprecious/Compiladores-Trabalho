# Compilador de Linguagem Fictícia (Calculadora)

Este projeto é um compilador simples desenvolvido em Java para uma linguagem fictícia de calculadora. Ele foi criado como parte de um trabalho acadêmico com foco na implementação das fases de análise léxica, sintática e semântica básica.

## 🚀 Funcionalidades

O compilador é capaz de interpretar e executar comandos de uma linguagem simples, oferecendo as seguintes funcionalidades:

- **Análise e Execução em Tempo Real**: O compilador não gera código executável em outro formato. Em vez disso, ele processa e avalia as instruções diretamente, retornando o resultado no console.
- **Estruturas de Comando**: Suporta comandos de declaração de variáveis, atribuição, leitura (simulada) e escrita.
- **Expressões Aritméticas**:
    - Avalia expressões matemáticas complexas.
    - **Precedência de Operadores**: Respeita a precedência padrão dos operadores (`^` > `*`, `/` > `+`, `-`).
    - **Associatividade**: A potência (`^`) é tratada com associatividade à direita.
- **Tipos de Dados**: Suporta números reais (com casas decimais) e strings literais.
- **Tabela de Símbolos**: Implementa uma tabela de símbolos para verificar se as variáveis foram declaradas antes de serem utilizadas.
- **Detecção de Erros**: Aponta erros léxicos (caracteres inválidos), sintáticos (estrutura de comando incorreta) e semânticos (uso de variável não declarada).

## 🧠 Gramática da Linguagem

A linguagem é baseada em uma gramática livre de contexto, projetada para ser simples, mas com precedência de operadores. A gramática foi refatorada para não conter recursividade à esquerda.

```

Program          → (Command)+ EOF

Command          → DeclareCommand | AssignCommand | ReadCommand | WriteCommand | ExpressionCommand

DeclareCommand   → declare Identifier ;

AssignCommand    → Identifier := Expression ;

ReadCommand      → leia ( Identifier ) ;

WriteCommand     → escreva ( (Identifier | Text) ) ;

ExpressionCommand→ Expression ;

Expression       → Term ExpressionTail
ExpressionTail   → + Term ExpressionTail | - Term ExpressionTail | λ

Term             → Power TermTail
TermTail         → \* Power TermTail | / Power TermTail | λ

Power            → Factor (^ Power)?

Factor           → Number | Identifier | ( Expression )

// Tokens
Identifier       → (a..z | A..Z) (a..z | A..Z | 0..9)\*
Number           → (0..9)+ (. (0..9)+)?
Text             → " (caracteres e espaços) "

````

## 🛠️ Como Executar

O projeto pode ser executado a partir da linha de comando usando a classe `Main.java`.

1.  **Pré-requisitos**:
    - Java Development Kit (JDK) 8 ou superior instalado.
    - Um ambiente para compilar e executar o código Java (como um IDE - IntelliJ, Eclipse - ou o terminal).

2.  **Compilar**:
    Navegue até a pasta `src` do projeto e compile os arquivos `.java`:
    ```bash
    javac calculator/*.java
    ```

3.  **Executar**:
    Na pasta `src`, execute a classe `Main`:
    ```bash
    java calculator.Main
    ```
    O programa iniciará um prompt (`>`) onde você pode digitar os comandos da linguagem fictícia. Para sair, digite `pare`.

### Exemplo de Uso:

````

> declare x;
> Variável 'x' declarada.
> x := (3 + 5) \* 2^2;
> Atribuição: x = 32.0
> escreva("O valor de x é");
> Saída: O valor de x é
> escreva(x);
> Saída: 32.0
> x \* 10;
> Resultado da expressão: 320.0
> pare
> Comando 'pare' recebido. Encerrando o programa.

```

## 📂 Estrutura do Projeto

O projeto é organizado na seguinte estrutura de arquivos:

```

.
└── src/
└── calculator/
├── Main.java          // Classe principal com o loop do console
├── Parser.java        // Analisador Sintático e Avaliador
├── Scanner.java       // Analisador Léxico
├── SymbolTable.java   // Tabela de Símbolos para variáveis
└── Token.java         // Definição dos tokens

```

---

*Este `README.md` foi gerado para o trabalho da Modalidade 1 do seu projeto de compiladores.*
```
