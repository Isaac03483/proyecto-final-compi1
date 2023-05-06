package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.List;

public class Math extends Instruction {

    private List<Instruction> instructions;
    private MathType mathType;

    public Math(int line, int column, MathType mathType, List<Instruction> instructions) {
        super(line, column);
        this.mathType = mathType;
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
