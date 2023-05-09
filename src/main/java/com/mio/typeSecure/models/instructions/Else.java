package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class Else extends Instruction{

    public List<Instruction> instructions;

    public Else(int line, int column, List<Instruction> instructions) {
        super(line, column);
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
