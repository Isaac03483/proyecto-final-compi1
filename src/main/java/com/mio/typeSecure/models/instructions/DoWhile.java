package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class DoWhile extends Instruction{

    public Instruction operation;
    public List<Instruction> instructions;

    public DoWhile(int line, int column, Instruction operation, List<Instruction> instructions) {
        super(line, column);
        this.operation = operation;
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {

        visitor.visit(this);
        return null;
    }
}
