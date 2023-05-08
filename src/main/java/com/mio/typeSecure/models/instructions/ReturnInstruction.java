package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class ReturnInstruction extends Instruction{

    private Instruction value;

    public ReturnInstruction(int line, int column, Instruction value) {
        super(line, column);
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
