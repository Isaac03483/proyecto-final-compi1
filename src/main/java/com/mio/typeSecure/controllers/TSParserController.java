package com.mio.typeSecure.controllers;

import com.mio.typeSecure.compiler.lexer.TSLexer;
import com.mio.typeSecure.compiler.parser.TSParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

public class TSParserController {


    public TSParserController() {
    }

    public void compile(String input){
        TSLexer tsLexer = new TSLexer(new StringReader(input));
        TSParser tsParser = new TSParser(tsLexer);
        try {
            tsParser.parse();
            System.out.println("Compilando...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
