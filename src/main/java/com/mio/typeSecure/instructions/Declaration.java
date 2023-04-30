package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Declaration extends Instruction{

    private DeclarationType type;
    private List<Instruction> instructions;

    public Declaration(int line, int column, DeclarationType type, List<Instruction> instructions) {
        super(line, column);
        this.type = type;
        this.instructions = instructions;
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
