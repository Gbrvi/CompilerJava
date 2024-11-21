/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.scanner;

/**
 *
 * @author gvalm
 */
public enum KeywordType {
    INT("int"),
    RETURN("return"),
    VOID("void"),
    MAIN("main");

    private final String keyword;

    KeywordType(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}


