package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Assignment extends Instruction{


    public String id;
    public VariableType variableType;
    public Instruction operation;

    public Assignment(int line, int column, String id, VariableType variableType, Instruction operation) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
        this.operation = operation;
    }

    public Assignment(int line, int column, String id, VariableType variableType) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
    }

    public Assignment(int line, int column, String id, Instruction operation) {
        super(line, column);
        this.id = id;
        this.operation = operation;
    }


    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
