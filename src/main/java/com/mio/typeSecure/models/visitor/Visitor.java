package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.instructions.*;
import com.mio.typeSecure.models.symbolTable.SymbolTable;

public abstract class Visitor {

    public SymbolTable table;

    public Visitor(SymbolTable table) {
        this.table = table;
    }

    public abstract Variable visit(Assignment assignment);
    public abstract Variable visit(BinaryOperation binaryOperation);
    public abstract Instruction visit(Break breakInstruction);
    public abstract Variable visit(Cast cast);
    public abstract Variable visit(CallFunction callFunction);
    public abstract void visit(ConsoleLog consoleLog);
    public abstract Instruction visit(Continue continueInstruction);
    public abstract void visit(Declaration declaration);
    public abstract Variable visit(DoWhile doWhile);
    public abstract Object visit(Else elseInstruction);
    public abstract Variable visit(For forInstruction);
    public abstract void visit(Function function);
    public abstract Object visit(If ifInstruction);
    public abstract Variable visit(MathInstruction mathInstruction);
    public abstract Variable visit(Parameter parameter);
    public abstract Variable visit(ReturnInstruction returnInstruction);
    public abstract Variable visit(StringInstruction stringInstruction);
    public abstract Variable visit(UnaryOperation unaryOperation);
    public abstract Variable visit(Value value);
    public abstract Variable visit(VariableDeclaration variableDeclaration);

    public abstract Variable visit(While whileInstruction);

}
