package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class ConsoleLog extends Instruction{

    public List<Instruction> instructions;

    public ConsoleLog(int line, int column, List<Instruction> instructions) {
        super(line, column);
        this.instructions = instructions;
    }

    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }
}
