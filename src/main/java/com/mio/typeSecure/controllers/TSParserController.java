package com.mio.typeSecure.controllers;

import com.mio.typeSecure.compiler.lexer.TSLexer;
import com.mio.typeSecure.compiler.parser.TSParser;
import com.mio.typeSecure.models.instructions.Instruction;
import com.mio.typeSecure.models.TSError;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TSParserController {


    public TSParserController() {
    }

    public List<String> compile(String input){
        TSLexer tsLexer = new TSLexer(new StringReader(input));
        List<TSError> errors = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        TSParser tsParser = new TSParser(instructions, tsLexer, errors);
        try {
            tsParser.parse();
            System.out.println("Compilando...");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return execute(instructions, errors);
    }

    public List<String> execute(List<Instruction> instructions, List<TSError> errors){
        List<String> out = null;
        //TODO: usar un visitor para verificar que todo el código funcione de la forma correcta

        if(!errors.isEmpty()){
            return errors.stream().map(TSError::toString).toList();
        }

        //TODO: usar un visitor para correr el programa.




        return out;
    }
}
