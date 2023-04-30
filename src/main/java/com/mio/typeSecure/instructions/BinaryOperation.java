package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class BinaryOperation extends Instruction{

    private Instruction leftOperation;
    private Instruction rightOperation;
    private OperationType operationType;

    public BinaryOperation(int line, int column, Instruction leftOperation, Instruction rightOperation, OperationType operationType) {
        super(line, column);
        this.leftOperation = leftOperation;
        this.rightOperation = rightOperation;
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
