package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class BinaryOperation extends Instruction{

    public Instruction leftOperation;
    public Instruction rightOperation;
    public OperationType operationType;

    public BinaryOperation(int line,
                           int column,
                           Instruction leftOperation,
                           Instruction rightOperation,
                           OperationType operationType) {

        super(line, column);
        this.leftOperation = leftOperation;
        this.rightOperation = rightOperation;
        this.operationType = operationType;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
