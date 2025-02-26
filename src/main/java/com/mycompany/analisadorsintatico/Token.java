/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.analisadorsintatico;

/**
 *
 * @author gvalm
 */
public class Token {
    TokenType type;
    String value;
    
    public Token(TokenType t, String v) {
        this.type = t;
        this.value = v;
    }
    
    public TokenType getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }
}
