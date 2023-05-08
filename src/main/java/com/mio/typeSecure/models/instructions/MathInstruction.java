package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class MathInstruction extends Instruction {

    private List<Instruction> instructions;
    private MathType mathType;

    public MathInstruction(int line, int column, MathType mathType, List<Instruction> instructions) {
        super(line, column);
        this.mathType = mathType;
        this.instructions = instructions;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
