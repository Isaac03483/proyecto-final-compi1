package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Cast extends Instruction {

    public Instruction value;
    public CastType type;

    public Cast(int line, int column, Instruction value, CastType type) {
        super(line, column);
        this.value = value;
        this.type = type;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
