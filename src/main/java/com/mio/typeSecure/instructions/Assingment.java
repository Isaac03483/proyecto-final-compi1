package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Assingment extends Instruction{


    public String id;
    public VariableType variableType;
    public Instruction operation;

    public Assingment(int line, int column, String id, VariableType variableType, Instruction operation) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
        this.operation = operation;
    }

    public Assingment(int line, int column, String id, VariableType variableType) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
    }

    public Assingment(int line, int column, String id, Instruction operation) {
        super(line, column);
        this.id = id;
        this.operation = operation;
    }



    @Override
    public Variable run(SymbolTable symbolTable) {
        return null;
    }

    @Override
    public Variable debug(SymbolTable symbolTable, List<Error> errorList) {
        return null;
    }
}
