package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class If extends Instruction{

    private Instruction operation;
    private List<Instruction> trueBlock;
    private Instruction falseBlock;

    public If(int line, int column, Instruction operation, List<Instruction> trueBlock, Instruction falseBlock) {
        super(line, column);
        this.operation = operation;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
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
