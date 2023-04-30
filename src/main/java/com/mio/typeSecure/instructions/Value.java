package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Value extends Instruction{

    ValueType valueType;
    String value;

    public Value(int line, int column, ValueType valueType, String value) {
        super(line, column);
        this.valueType = valueType;
        this.value = value;
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
