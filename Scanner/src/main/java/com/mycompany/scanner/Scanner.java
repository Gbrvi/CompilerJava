/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.scanner;


import java.io.InputStream;
import java.io.IOException;
/**
 *
 * @author gvalm
 */
public class Scanner {

    InputStream in; // Fluxo de entrada para leitura de caracteres
    boolean back; // Retroceder um caracter ou nao
    char currentChar; // Caracter atual
    boolean error; // Erro 
    StringBuffer tokenString; // Strings mutáveis
    
    public Scanner (InputStream i)
    {
        this.in = i; // Inicializa o fluxo de entrada 
        back = false; // Inicializa sem retroceder
    }
    
    private boolean nextChar() throws IOException {
        if(!back)
        {
            currentChar = (char) in.read(); // Se for True, ler caractere
            
        }
        tokenString.append(currentChar); // Adiciona atual caracter em tokenString
        back = false; // Sta como falso tag de retrocesso
        return true; 
    }
    
    public void cleartokenString()
    {
        tokenString.deleteCharAt(tokenString.length() - 1); // Limpa o token
    }
    
    // Mtodo para retornar ao ultimo caracter
    public void setBack(){
        back = true; //Flag que informa que vai retroceder um caracter
        cleartokenString(); // Limpa o ultimo caracter
    }
    
    public Token lex(){
     
        
        int state; // Estado da maquina
        tokenString = new StringBuffer(); // Buffer do token
        
        error = false; 
        state = 0;
        
        try{
            while(!error)
            {
                switch (state) {
                case 0:
                    nextChar(); 
                    if (Character.isLetter(currentChar)) // Se for letra, pode ser identificador ou keyword
                        state = 1;
                    else if (Character.isDigit(currentChar)) // Se for digito, pode ser numero inteiro
                        state = 3;
                    else if (currentChar == '>') // Pode ser comparador maior
                        state = 5;
                    else if (currentChar == '<') //Pode ser comparador menor
                        state = 8;
                    else if (currentChar == '(') 
                        return new Token(TokenType.PARABERTO, tokenString.toString()); // Retorna token de parentese aberto
                    else if (currentChar == ')')
                        return new Token(TokenType.PARFECHADO, tokenString.toString()); // Retorna token de parentese fechado
                       else if (   (currentChar == ' '  || 
	        		    currentChar == '\n' ||
	        		    currentChar == '\t' ||
	        		    currentChar == '\r') ) // / Ignora espaços em branco e caracteres de controle
                           
                        cleartokenString(); 

                       else // Nenhum caso anterior, marca como error
                        error = true;
                    break;      
                
                case 1: // Processa identificador ou keyword
                    nextChar();
                    if(!Character.isLetterOrDigit(currentChar)) //Estado 2 caso nao seja letra ou numero 
                        state = 2;
                    break;
                case 2: // Verifica palavra chave
                    setBack(); 
                    
                     for (KeywordType keyword : KeywordType.values()) {
                        if (keyword.getKeyword().equals(tokenString.toString())) {
                            return new Token(TokenType.KEYWORD, tokenString.toString()); 
                        }
                     }
                     
                    nextChar(); // Cheirinho de gambiarra resolver dps
                    state = 11;  // Vai para o estado 11 para tratar identificadores
                    break;
   
                    
                    
                case 3:  // Estado para processamento de números inteiros
                    nextChar();
                    if(!Character.isDigit(currentChar));
                        state = 4;  // Vai para o estado 4 se não for dígito
                    break;    
                case 4: setBack(); // Finaliza a leitura de números inteiros
                    return new Token(TokenType.INTCONST, tokenString.toString());
                
                case 5: nextChar(); // Estado para '>'
                    if(currentChar == '=')
                        state = 6;
                    else state = 7;
                    break;  
                case 6: return new Token(TokenType.MENORIGUAL, tokenString.toString()); // Retorna token de maior ou igual (">=")
                
                case 7: setBack(); // Finaliza a leitura do token '>'
                    return new Token(TokenType.MENOR, tokenString.toString());
                case 8: nextChar();
                    if(currentChar == '=')
                        state = 9;  // Se encontrar '=', vai para o estado 9 (menor ou igual)
                    else state = 10;
                    break;
                case 9: return new Token(TokenType.MAIORIGUAL, tokenString.toString());
                
                case 10: setBack(); // Finaliza a leitura do token '<'
                    return new Token(TokenType.MAIOR, tokenString.toString());
                
                case 11: ; // Verifica se é identificador (mas primeiro ve se é palavra chave no status 2)
                    return new Token(TokenType.IDENT, tokenString.toString());

   
                default: error = true;
                
                }
            } 
            return null;
        } catch(IOException e )
        {
            return null;
        }
                
    }
}
