package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.instructions.*;

import java.util.List;

public class Runner extends Visitor{

    private List<String> out;

    public Runner(SymbolTable table, List<String> out) {
        super(table);
        this.out = out;
    }

    @Override
    public Variable visit(Assignment assignment) {
        return null;
    }

    @Override
    public Variable visit(BinaryOperation binaryOperation) {
        return null;
    }

    @Override
    public void visit(Break breakInstruction) {

    }

    @Override
    public Variable visit(Cast cast) {
        return null;
    }

    @Override
    public Variable visit(CallFunction callFunction) {
        return null;
    }

    @Override
    public void visit(ConsoleLog consoleLog) {

    }

    @Override
    public void visit(Continue continueInstruction) {

    }

    @Override
    public void visit(Declaration declaration) {

    }

    @Override
    public Variable visit(DoWhile doWhile) {
        return null;
    }

    @Override
    public Variable visit(Else elseInstruction) {
        return null;
    }

    @Override
    public Variable visit(For forInstruction) {
        return null;
    }

    @Override
    public void visit(Function function) {

    }

    @Override
    public Variable visit(If ifInstruction) {
        return null;
    }

    @Override
    public Variable visit(MathInstruction mathInstruction) {
        return null;
    }

    @Override
    public Variable visit(ReturnInstruction returnInstruction) {
        return null;
    }

    @Override
    public Variable visit(StringInstruction stringInstruction) {
        return null;
    }

    @Override
    public Variable visit(UnaryOperation unaryOperation) {
        return null;
    }

    @Override
    public Variable visit(Value value) {
        return null;
    }

    @Override
    public Variable visit(VariableDeclaration variableDeclaration) {
        return null;
    }

    @Override
    public Variable visit(While whileInstruction) {
        return null;
    }
}
