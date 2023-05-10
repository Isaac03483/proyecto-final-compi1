package com.mio.typeSecure.controllers;

import com.mio.typeSecure.compiler.lexer.TSLexer;
import com.mio.typeSecure.compiler.parser.TSParser;
import com.mio.typeSecure.models.instructions.Instruction;
import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.instructions.SymbolTable;
import com.mio.typeSecure.models.visitor.Debugger;
import com.mio.typeSecure.models.visitor.Visitor;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class TSParserController {


    public TSParserController() {
    }

    public List<String> compile(String input){
        TSLexer tsLexer = new TSLexer(new StringReader(input));
        List<TSError> errors = new ArrayList<>();
        List<Instruction> instructions = null;
        TSParser tsParser = new TSParser(tsLexer, errors);
        try {
            instructions = (List<Instruction>)tsParser.parse().value;
            System.out.println("Compilando...");
        } catch (Exception e) {
//            System.err.println(e.getMessage());
        }

        return execute(instructions, errors);
    }

    public List<String> execute(List<Instruction> instructions, List<TSError> errors){
        List<String> out = new ArrayList<>();

        if(instructions == null){
            return errors.stream().map(TSError::toString).toList();

        }

        //TODO: usar un visitor para verificar que todo el código funcione de la forma correcta
        SymbolTable table = new SymbolTable();
        Visitor debugger = new Debugger(table, errors);

        instructions.forEach(instruction -> {
            instruction.accept(debugger);
        });

        if(!errors.isEmpty()){
            return errors.stream().map(TSError::toString).toList();
        }

        table.getVariableList().forEach(System.out::println);
        //TODO: usar un visitor para correr el programa.

        return out;
    }
}
