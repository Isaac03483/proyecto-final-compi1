package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Break extends Instruction{

    public Break(int line, int column) {
        super(line, column);
    }

    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

}
