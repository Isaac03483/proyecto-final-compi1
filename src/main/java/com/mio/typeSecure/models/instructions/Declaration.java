package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class Declaration extends Instruction{

    public DeclarationType type;
    public List<Instruction> instructions;

    public Declaration(int line, int column, DeclarationType type, List<Instruction> instructions) {
        super(line, column);
        this.type = type;
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }
}
