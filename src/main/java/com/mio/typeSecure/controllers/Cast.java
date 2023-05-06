package com.mio.typeSecure.controllers;

import com.mio.typeSecure.instructions.CastType;
import com.mio.typeSecure.instructions.Instruction;
import com.mio.typeSecure.instructions.SymbolTable;
import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Cast extends Instruction {

    private String value;
    private CastType type;

    public Cast(int line, int column, String value, CastType type) {
        super(line, column);
        this.value = value;
        this.type = type;
    }

    @Override
    public Object run(SymbolTable symbolTable) {
        return null;
    }

    @Override
    public Object debug(SymbolTable symbolTable, List<Error> errorList) {
        return null;
    }
}
