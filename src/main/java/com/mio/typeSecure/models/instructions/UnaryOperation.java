package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class UnaryOperation extends Instruction{

    public Instruction rightInstruction;
    public OperationType operationType;

    public UnaryOperation(int line, int column,Instruction rightInstruction, OperationType operationType) {
        super(line, column);
        this.rightInstruction = rightInstruction;
        this.operationType = operationType;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "UnaryOperation{" +
                "rightInstruction=" + rightInstruction +
                ", operationType=" + operationType +
                '}';
    }
}
