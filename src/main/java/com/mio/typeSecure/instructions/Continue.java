package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Continue extends Instruction{

    public Continue(int line, int column) {
        super(line, column);
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
