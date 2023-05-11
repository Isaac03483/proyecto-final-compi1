package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class MathInstruction extends Instruction {

    public List<Instruction> instructions;
    public MathType mathType;

    public MathInstruction(int line, int column, MathType mathType, List<Instruction> instructions) {
        super(line, column);
        this.mathType = mathType;
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
