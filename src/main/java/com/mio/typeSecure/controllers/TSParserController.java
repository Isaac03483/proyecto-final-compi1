package com.mio.typeSecure.controllers;

import com.mio.typeSecure.compiler.lexer.TSLexer;
import com.mio.typeSecure.compiler.parser.TSParser;
import com.mio.typeSecure.instructions.Instruction;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TSParserController {


    public TSParserController() {
    }

    public List<String> compile(String input){
        TSLexer tsLexer = new TSLexer(new StringReader(input));
        List<String> out = new ArrayList<>();
        TSParser tsParser = new TSParser(tsLexer, out);
        try {
            tsParser.parse();
            System.out.println("Compilando...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return out;
    }

    public void execute(List<Instruction> instructions, List<String> out){

    }
}
