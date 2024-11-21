package com.mycompany.scanner;

import java.io.FileInputStream;

public class Main {

	/**
	 * @param args
	 */
    public static void main(String[] args){
         try{	
		FileInputStream file = new FileInputStream("C:\\Users\\gvalm\\Documents\\NetBeansProjects\\Scanner\\bin\\com\\mycompany\\scanner\\teste_lexico.txt");
		Token t;
                
                System.out.println("OI");
		
		Scanner l =  new Scanner(file);
		while ( (t = l.lex()) != null) {  
			if (t.getType() == TokenType.IDENT)
			    System.out.println("IDENTIFICADOR" + "  Value=" + t.getValue());
			else if (t.getType() == TokenType.INTCONST)
				System.out.println("CONSTANTE INTEIRA" + "Value=" + t.getValue());
			else if (t.getType() == TokenType.MENOR)
				System.out.println("MENOR" + "  Value= " + t.getValue()); 
			else if (t.getType() == TokenType.MENORIGUAL)
				System.out.println("MENORIGUAL" + "  Value= " + t.getValue());
			else if (t.getType() == TokenType.MAIOR)
				System.out.println("MAIOR" + " Value= " + t.getValue());
			else if (t.getType() == TokenType.MAIORIGUAL)
				System.out.println("MAIORIGUAL" + " Value=" + t.getValue());
			else if (t.getType() == TokenType.PARABERTO)
				System.out.println("PARABERTO" + "  Value=" + t.getValue());
			else if (t.getType() == TokenType.PARFECHADO)
				System.out.println("PARFECHADO" + "  Value=" + t.getValue());
			else if (t.getType() == TokenType.PONTO) {
				  System.out.println("PONTO" + " Value=" + t.getValue());
				  break;
			     }
                        else if (t.getType() == TokenType.KEYWORD)
                        {
                            System.out.println("KEYWORD" + "VALUE= " + t.getValue());
                        }
                    }
	    } 
		catch (Exception e){
		  System.out.println("Erro abrindo o arquivo");
	     }

	}

}