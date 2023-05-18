package com.mio.typeSecure.models.instructions;

import com.mio.typeSecure.models.symbolTable.SymbolTable;
import com.mio.typeSecure.models.visitor.Visitor;

import java.util.List;

public class Function extends Instruction{

    public String id;
    public List<Instruction> parametersInstr;
    public List<Instruction> instructions;
    public ReturnType returnType;
    public SymbolTable parentTable;
    public List<Variable> parametersInFun;

    public Function(int line,
                    int column,
                    String id,
                    List<Instruction> parameters,
                    List<Instruction> instructions,
                    ReturnType returnType) {

        super(line, column);
        this.id = id;
        this.parametersInstr = parameters;
        this.instructions = instructions;
        this.returnType = returnType;
    }


    @Override
    public Variable accept(Visitor visitor) {
        visitor.visit(this);
        return null;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id='" + id + '\'' +
                ", returnType=" + returnType +
                ", parentTable=" + parentTable +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
