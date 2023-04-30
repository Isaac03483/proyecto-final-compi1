package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class For extends Instruction{

    private Instruction assignmentBlock;
    private Instruction operationBlock;
    private Instruction incrementBlock;
    private List<Instruction> instructions;

    public For(int line, int column, Instruction assignmentBlock, Instruction operationBlock, Instruction incrementBlock, List<Instruction> instructions) {
        super(line, column);
        this.assignmentBlock = assignmentBlock;
        this.operationBlock = operationBlock;
        this.incrementBlock = incrementBlock;
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
