package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Continue extends Instruction{

    public Continue(int line, int column) {
        super(line, column);
    }

    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

}
