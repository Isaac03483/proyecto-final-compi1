package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class CallFunction extends Instruction {

    public String id;
    public List<Instruction> instructions;

    public CallFunction(int line, int column, String id, List<Instruction> instructions) {
        super(line, column);
        this.id = id;
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}