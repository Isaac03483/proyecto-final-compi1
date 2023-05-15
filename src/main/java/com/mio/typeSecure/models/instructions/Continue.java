package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Continue extends Instruction{

    public Continue(int line, int column) {
        super(line, column);
    }

    @Override
    public Instruction accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
