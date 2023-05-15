package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class StringInstruction extends Instruction{

    public Instruction value;
    public StringType type;
    public Instruction instruction;

    public StringInstruction(int line, int column, Instruction value, StringType type, Instruction instruction) {
        super(line, column);
        this.value = value;
        this.type = type;
        this.instruction = instruction;
    }

    public Instruction getValue() {
        return value;
    }

    public void setValue(Instruction value) {
        this.value = value;
    }

    public StringType getType() {
        return type;
    }

    public void setType(StringType type) {
        this.type = type;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstructions(Instruction instruction) {
        this.instruction = instruction;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
