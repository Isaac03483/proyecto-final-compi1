package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Return extends Instruction{

    private Instruction value;

    public Return(int line, int column, Instruction value) {
        super(line, column);
        this.value = value;
    }

    @Override
    public Instruction run(SymbolTable symbolTable) {
        return null;
    }

    @Override
    public Instruction debug(SymbolTable symbolTable, List<Error> errorList) {

        return this;
    }
}
