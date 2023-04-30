package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class UnaryOperation extends Instruction{

    private Instruction rightInstruction;
    private OperationType operationType;

    public UnaryOperation(int line, int column,Instruction rightInstruction, OperationType operationType) {
        super(line, column);
        this.rightInstruction = rightInstruction;
        this.operationType = operationType;
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
