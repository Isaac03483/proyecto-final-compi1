package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class String extends Instruction{

    private String value;
    private StringType type;
    private List<Instruction> instructions;

    public String(int line, int column, String value, StringType type, List<Instruction> instructions) {
        super(line, column);
        this.value = value;
        this.type = type;
        this.instructions = instructions;
    }


    @Override
    public Object run(SymbolTable symbolTable) {
        return null;
    }

    @Override
    public Object debug(SymbolTable symbolTable, List<Error> errorList) {
        return null;
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
}
