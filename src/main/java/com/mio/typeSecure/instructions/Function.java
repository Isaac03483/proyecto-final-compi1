package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Function extends Instruction{

    private String id;
    private List<Parameter> parameters;
    private List<Instruction> instructions;
    private ReturnType returnType;
    private SymbolTable symbolTable;

    public Function(int line, int column, String id, List<Parameter> parameters, List<Instruction> instructions, ReturnType returnType) {
        super(line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
        this.returnType = returnType;
    }

    public Function(int line, int column, String id, List<Parameter> parameters, List<Instruction> instructions) {
        super(line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
    }

    @Override
    public Variable run(SymbolTable symbolTable) {
        this.symbolTable = symbolTable.clone();
        return null;
    }

    @Override
    public Variable debug(SymbolTable symbolTable, List<Error> errorList) {
        return null;
    }


}
