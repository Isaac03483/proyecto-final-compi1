package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.instructions.*;

public abstract class Visitor {

    private SymbolTable table;

    public abstract Variable visit(Assignment assignment);
    public abstract Variable visit(BinaryOperation binaryOperation);
    public abstract void visit(Break breakInstruction);
    public abstract Variable visit(Cast cast);
    public abstract Variable visit(CallFunction callFunction);
    public abstract void visit(ConsoleLog consoleLog);
    public abstract void visit(Continue continueInstruction);
    public abstract void visit(Declaration declaration);
    public abstract void visit(DoWhile doWhile);
    public abstract void visit(For forInstruction);
    public abstract void visit(Function function);
    public abstract void visit(If ifInstruction);
    public abstract Variable visit(MathInstruction mathInstruction);
    public abstract Variable visit(ReturnInstruction returnInstruction);
    public abstract Variable visit(StringInstruction stringInstruction);
    public abstract Variable visit(UnaryOperation unaryOperation);
    public abstract Variable visit(Value value);
    public abstract void visit(While whileInstruction);
}
