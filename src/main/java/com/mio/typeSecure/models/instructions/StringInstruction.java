package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class StringInstruction extends Instruction{

    private String value;
    private StringType type;
    private Instruction instruction;

    public StringInstruction(int line, int column, String value, StringType type, Instruction instruction) {
        super(line, column);
        this.value = value;
        this.type = type;
        this.instruction = instruction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
