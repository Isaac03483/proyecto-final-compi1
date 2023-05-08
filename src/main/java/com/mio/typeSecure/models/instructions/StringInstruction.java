package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class StringInstruction extends Instruction{

    private String value;
    private StringType type;
    private List<Instruction> instructions;

    public StringInstruction(int line, int column, String value, StringType type, List<Instruction> instructions) {
        super(line, column);
        this.value = value;
        this.type = type;
        this.instructions = instructions;
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

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public void accept(Visitor visitor) {

    }
}
