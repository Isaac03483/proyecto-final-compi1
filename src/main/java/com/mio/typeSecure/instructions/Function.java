package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Function extends Instruction{

    private String id;
    private List<Parameter> parameters;
    private List<Instruction> instructions;
    private FunctionType functionType;

    public Function(int line, int column, String id, List<Parameter> parameters, List<Instruction> instructions, FunctionType functionType) {
        super(line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
        this.functionType = functionType;
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
