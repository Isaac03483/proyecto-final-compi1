package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;
import java.util.Objects;

public class Function extends Instruction{

    public String id;
    public List<Instruction> parameters;
    public List<Instruction> instructions;
    public ReturnType returnType;
    public SymbolTable symbolTable;

    public Function(int line,
                    int column,
                    String id,
                    List<Instruction> parameters,
                    List<Instruction> instructions,
                    ReturnType returnType) {

        super(line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
        this.returnType = returnType;
    }


    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }
}
