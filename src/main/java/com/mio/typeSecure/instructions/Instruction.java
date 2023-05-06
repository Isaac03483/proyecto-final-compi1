package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;
import java.util.Objects;

public abstract class Instruction {

    int line;
    int column;

    public Instruction(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public abstract Object run(SymbolTable symbolTable);

    public abstract Object debug(SymbolTable symbolTable, List<Error> errorList);
}
