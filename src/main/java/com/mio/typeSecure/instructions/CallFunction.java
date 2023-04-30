package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class CallFunction extends Instruction{

    private String id;
    private List<Instruction> instructions;

    public CallFunction(int line, int column, String id, List<Instruction> instructions) {
        super(line, column);
        this.id = id;
        this.instructions = instructions;
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
