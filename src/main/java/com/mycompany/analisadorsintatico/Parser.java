/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisadorsintatico;


/**
 *
 * @author gvalm
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Parser {
    private final Scanner scanner;
    private Token currentToken;

    public Parser(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
        nextToken();
        System.out.println("Parser inicializado.");
    }

    private void nextToken() {
        currentToken = scanner.lex();
        
    }

    private void expect(TokenType expectedType) throws Exception {
        if (currentToken != null && currentToken.getType() == expectedType) {
            nextToken();
        } else {
            throw new Exception("Erro de sintaxe: Esperava " + expectedType + " mas encontrou " +
                    (currentToken != null ? currentToken.getType() : "EOF"));
        }
    }

    public void parse() throws Exception {
        System.out.println("Iniciando parse...");
        Program();
        System.out.println("Parse concluído!");
    }

    private void Program() throws Exception {
        // <programa> → <declarações lista>
        System.out.println("Parsing do programa...");
        DeclarationsList();
    }

    private void DeclarationsList() throws Exception {
        //< declarações lista > → < declarações lista> <declarações> | <declarações>
        System.out.println("Parsing da lista de declarações...");
        Declaration();
        while (currentToken != null && isTypeToken(currentToken)) {
            Declaration();
        }
    }

    private void Declaration() throws Exception {
        //<declarações> → < declaração var > | < declaração func>
        System.out.println("Parsing de uma declaração...");
        if (isTypeToken(currentToken)) {
            Type();
            expect(TokenType.IDENT);

            if (currentToken.getType() == TokenType.PARABERTO) {
                FunctionDeclaration();
            } else {
                VariableDeclaration();
            }
        } else {
            throw new Exception("Erro de sintaxe: Tipo esperado.");
        }
    }

    private void VariableDeclaration() throws Exception {
        // < declaração var > → <tipo> ident ; | <tipo> ident [ contint ] ;
        System.out.println("Parsing de uma declaração de variável...");
        if (currentToken.getType() == TokenType.COLCHETEABERTO) {
            expect(TokenType.COLCHETEABERTO);
            expect(TokenType.INTCONST);
            expect(TokenType.COLCHETEFECHADO);
        }
        expect(TokenType.PONTOVIRG);
    }

    private void FunctionDeclaration() throws Exception {
        //  <declaração func>→ < tipo > ident ( < par formais> ) <decl composto>
        System.out.println("Parsing de uma declaração de função...");
        expect(TokenType.PARABERTO);
        FormalParameters();
        expect(TokenType.PARFECHADO);
        CompoundStatement();
    }

    private void FormalParameters() throws Exception {
        // < par formais> → < lista par formais > | 
        System.out.println("Parsing dos parâmetros formais...");
        if (isTypeToken(currentToken)) {
            ParameterList();
        }
    }

    private void ParameterList() throws Exception {
        // < lista par formais > → <parametro> , < lista par formais > | < parametro >
        System.out.println("Parsing da lista de parâmetros...");
        Parameter();
        while (currentToken != null && currentToken.getType() == TokenType.VIRGULA) {
            expect(TokenType.VIRGULA);
            Parameter();
        }
    }

    private void Parameter() throws Exception {
        // → <tipo> ident | <tipo> ident [ ]
        System.out.println("Parsing de um parâmetro...");
        Type();
        expect(TokenType.IDENT);

        if (currentToken.getType() == TokenType.COLCHETEABERTO) {
            expect(TokenType.COLCHETEABERTO);
            expect(TokenType.COLCHETEFECHADO);
        }
    }

    private void CompoundStatement() throws Exception {
        // <decl composto> → { <declarações locais> <lista comandos> }
        System.out.println("Parsing de um bloco composto...");
        expect(TokenType.CHAVEABERTA);
        LocalDeclarations();
        CommandList();
        expect(TokenType.CHAVEFECHADA);
    }

    private void LocalDeclarations() throws Exception {
        // <declarações locais> → <declarações locais> < declaração var | vazio
        System.out.println("Parsing de declarações locais...");
        while (isTypeToken(currentToken)) {
            Type();
            expect(TokenType.IDENT);
            VariableDeclaration();
        }
    }

    private void CommandList() throws Exception {
        // ComandList → <comando> <lista de comandos> | 
        System.out.println("Parsing da lista de comandos...");
        while (currentToken != null && isCommandToken(currentToken)) {
            Command();
        }
    }

    private void Command() throws Exception {
        // < comando expressão > | < comando composto > | <comando seleção > | <comando iteração > | <comando retorno>
        System.out.println("Parsing de um comando...");
        switch (currentToken.getType()) {
            case IDENT:
                ExpressionCommand();
                break;
            case CHAVEABERTA:
                CompoundStatement();
                break;
            case IF:
                parseSelectionCommand();
                break;
            case WHILE:
                IterationCommand();
                break;
            case RETURN:
                ReturnCommand();
                break;
            default:
                throw new Exception("Erro de sintaxe: Comando inválido.");
        }
    }

    private void ExpressionCommand() throws Exception {
        // < comando expressão > → <expressão> ; |
        System.out.println("Parsing de um comando de expressão...");
        Expression();
        expect(TokenType.PONTOVIRG);
    }

    private void parseSelectionCommand() throws Exception {
        // if (<expressão>) < comando> | If ( <expressão>) < comando> else <comando>
        System.out.println("Parsing de um comando de seleção...");
        expect(TokenType.IF);
        expect(TokenType.PARABERTO);
        Expression();
        expect(TokenType.PARFECHADO);
        Command();

        if (currentToken.getType() == TokenType.ELSE) {
            expect(TokenType.ELSE);
            Command();
        }
    }

    private void IterationCommand() throws Exception {
        // while (<expressão>) <comando>
        System.out.println("Parsing de um comando de iteração...");
        expect(TokenType.WHILE);
        expect(TokenType.PARABERTO);
        Expression();
        expect(TokenType.PARFECHADO);
        Command();
    }

    private void ReturnCommand() throws Exception {
        // <comando retorno> → return; | return <expressão>)
        System.out.println("Parsing de um comando de retorno...");
        expect(TokenType.RETURN);
        if (currentToken.getType() != TokenType.PONTOVIRG) {
            Expression();
        }
        expect(TokenType.PONTOVIRG);
    }

    private void Expression() throws Exception {
        System.out.println("Parsing de uma expressão...");
        if (currentToken.getType() == TokenType.IDENT) {
        expect(TokenType.IDENT); // Consome o identificador
        if (currentToken.getType() == TokenType.ATRIBUIR) { // Trata o operador '='
            expect(TokenType.ATRIBUIR); // Consome o '='
            Expression(); // Analisa o lado direito da atribuição
            return;
        }
    }
    // Caso contrário, analisa como uma expressão simples
        SimpleExpression();
    }

    private void SimpleExpression() throws Exception {
        // <expressão simples> → <expressões soma> < op relacional> <expressões soma> | <expressões soma>
        System.out.println("Parsing de uma expressão simples...");
        AdditiveExpression();
    }

    private void AdditiveExpression() throws Exception {
        // <expressões soma> → <expressões soma > <op aditivo> <termo> | <termo>|
        Term(); // Analisa o primeiro termo da expressão

    // Enquanto o próximo token for um operador de soma/subtração (+ ou -)
        while (currentToken != null && 
            (currentToken.getType() == TokenType.SOMAR || currentToken.getType() == TokenType.SUBTRAIR)) {
         nextToken(); // Consome o operador (+ ou -)
         Term(); // Analisa o próximo termo
        }
    }

    private void Term() throws Exception {
        // Termo → <termo> <op mult> <fator> | <fator>
        System.out.println("Parsing de um termo...");
        Factor();
    }

    private void Factor() throws Exception {
        // <fator> → ( <expressão> ) | <var> | <ativação> | contint
        System.out.println("Parsing de um fator...");
        if (currentToken.getType() == TokenType.INTCONST) {
            expect(TokenType.INTCONST);
        } else if (currentToken.getType() == TokenType.IDENT) {
            expect(TokenType.IDENT);
        } else {
            throw new Exception("Erro de sintaxe: Fator inválido.");
        }
    }

    private void Type() throws Exception {
        // Type -> int | void
        System.out.println("Parsing de um tipo...");
        if (isTypeToken(currentToken)) {
            expect(currentToken.getType());
        } else {
            throw new Exception("Erro de sintaxe: Tipo inválido.");
        }
    }

    private boolean isTypeToken(Token token) {
        return token.getType() == TokenType.TIPO || token.getType() == TokenType.VAZIO;
    }

    private boolean isCommandToken(Token token) {
        return token.getType() == TokenType.IF || token.getType() == TokenType.WHILE || token.getType() == TokenType.RETURN || token.getType() == TokenType.IDENT || token.getType() == TokenType.CHAVEABERTA;
    }

    public static void main(String[] args) {
        String code = "int []"; // Código de teste
        InputStream inputStream = new ByteArrayInputStream(code.getBytes());

        try {
            FileInputStream file = new FileInputStream("C:\\Users\\gvalm\\Documents\\NetBeansProjects\\AnalisadorSintatico\\src\\main\\java\\com\\mycompany\\analisadorsintatico\\teste.txt");
            Parser parser = new Parser(file);
            parser.parse();
            System.out.println("Parsing concluído com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro durante o parsing: " + e.getMessage());
        }
    }
}