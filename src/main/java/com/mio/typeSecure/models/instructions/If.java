package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

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
    public void accept(Visitor visitor) {

    }
}
