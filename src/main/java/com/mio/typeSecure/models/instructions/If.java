package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class If extends Instruction{

    public Instruction operation;
    public List<Instruction> trueBlock;
    public Instruction falseBlock;

    public If(int line, int column, Instruction operation, List<Instruction> trueBlock, Instruction falseBlock) {
        super(line, column);
        this.operation = operation;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
