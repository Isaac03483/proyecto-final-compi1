package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class VariableDeclaration extends Instruction{

    public String id;
    public VariableType variableType;
    public Instruction instruction;

    public VariableDeclaration(int line, int column, String id, VariableType variableType, Instruction instruction) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
        this.instruction = instruction;
    }

    public VariableDeclaration(int line, int column, String id, VariableType variableType) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
