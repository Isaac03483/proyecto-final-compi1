package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public abstract class Instruction {

    int line;
    int column;

    public Instruction(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public abstract Variable run(SymbolTable symbolTable);

    public abstract Variable debug(SymbolTable symbolTable, List<Error> errorList);
}
