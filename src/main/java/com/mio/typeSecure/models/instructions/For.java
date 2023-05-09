package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class For extends Instruction{

    public Instruction assignmentBlock;
    public Instruction operationBlock;
    public Instruction incrementBlock;
    public List<Instruction> instructions;

    public For(int line,
               int column,
               Instruction assignmentBlock,
               Instruction operationBlock,
               Instruction incrementBlock,
               List<Instruction> instructions) {

        super(line, column);
        this.assignmentBlock = assignmentBlock;
        this.operationBlock = operationBlock;
        this.incrementBlock = incrementBlock;
        this.instructions = instructions;
    }


    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }
}
