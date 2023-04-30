package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class DoWhile extends Instruction{

    private Instruction operation;
    private List<Instruction> instructions;

    public DoWhile(int line, int column, Instruction operation, List<Instruction> instructions) {
        super(line, column);
        this.operation = operation;
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
