package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class While extends Instruction{

    public Instruction operation;
    public List<Instruction> instructions;

    public While(int line, int column, Instruction operation, List<Instruction> instructions) {
        super(line, column);
        this.operation = operation;
        this.instructions = instructions;
    }


    @Override
    public Variable accept(Visitor visitor) {

        return visitor.visit(this);
    }
}
