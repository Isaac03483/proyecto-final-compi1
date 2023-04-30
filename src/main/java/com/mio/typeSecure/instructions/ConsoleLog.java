package com.mio.typeSecure.instructions;

import com.mio.typeSecure.utils.Error;

import java.util.ArrayList;
import java.util.List;

public class ConsoleLog extends Instruction{

    private final List<Instruction> instructions;

    public ConsoleLog(int line, int column, List<Instruction> instructions) {
        super(line, column);
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
