package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Value extends Instruction{

    String value;
    ValueType valueType;

    public Value(int line, int column, String value, ValueType valueType) {
        super(line, column);
        this.value = value;
        this.valueType = valueType;
    }

    @Override
    public Variable run(SymbolTable symbolTable) {

        return null;
    }

    @Override
    public Variable debug(SymbolTable symbolTable, List<Error> errorList) {
        return null;
    }


}
