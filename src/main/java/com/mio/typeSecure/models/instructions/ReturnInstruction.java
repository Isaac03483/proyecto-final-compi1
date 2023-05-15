package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class ReturnInstruction extends Instruction{

    public Instruction value;

    public ReturnInstruction(int line, int column, Instruction value) {
        super(line, column);
        this.value = value;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
