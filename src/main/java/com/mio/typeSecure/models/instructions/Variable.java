package com.mio.typeSecure.models.instructions;

public class Variable implements Cloneable{

    public String id;
    public VariableType variableType;
    public String value = "undefined";
    public DeclarationType declarationType;
    public int line;
    public int column;


    @Override
    public String toString() {
        return "Variable{" +
                "id='" + id + '\'' +
                ", variableType=" + variableType +
                ", value='" + value + '\'' +
                ", declarationType=" + declarationType +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    @Override
    public Variable clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Variable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
