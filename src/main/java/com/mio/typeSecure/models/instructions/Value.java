package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

public class Value extends Instruction{

    String value;
    ValueType valueType;

    public Value(int line, int column, String value, ValueType valueType) {
        super(line, column);
        this.value = value;
        this.valueType = valueType;
    }

    @Override
    public Variable accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
