package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public abstract class Instruction {

    public int line;
    public int column;

    public Instruction(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public abstract Object accept(Visitor visitor);
}
