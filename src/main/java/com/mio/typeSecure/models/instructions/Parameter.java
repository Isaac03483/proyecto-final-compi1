package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.Objects;

public class Parameter extends Instruction{

    public String id;
    public VariableType variableType;

    public Parameter(int line, int column, String id, VariableType variableType) {
        super(line, column);
        this.id = id;
        this.variableType = variableType;
    }


    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        return Objects.equals(id, parameter.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
