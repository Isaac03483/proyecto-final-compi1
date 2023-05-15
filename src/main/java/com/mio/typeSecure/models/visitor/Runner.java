package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.instructions.*;

import java.util.List;

public class Runner extends Visitor{

    private List<String> out;
    private List<TSError> errorList;

    public Runner(SymbolTable table, List<String> out, List<TSError> errorList) {
        super(table);
        this.out = out;
        this.errorList = errorList;
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
    public Break visit(Break breakInstruction) {
        return null;
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
    public Continue visit(Continue continueInstruction) {

        return null;
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
    public Variable visit(Parameter parameter){
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
        Variable variable = new Variable();
        switch (value.valueType){
            case NUMBER_VALUE -> {
                variable.variableType = VariableType.NUMBER;
                variable.value = value.value;
                return variable;
            }
            case ID -> {
                Variable varInTable = this.table.findById(value.value);
                if(varInTable == null){
                    this.errorList.add(
                            new TSError(
                                    value.line,
                                    value.column,
                                    "No se encontró la variable: "+value.value
                            )
                    );
                    return null;
                }
                variable = varInTable.clone();
                return variable;
            }

            case BOOLEAN -> {
                variable.variableType = VariableType.BOOLEAN;
                variable.value = value.value;
                return variable;
            }

            case STRING_VALUE -> {
                variable.variableType = VariableType.STRING;
                variable.value = value.value;
                return variable;
            }

            case BIG_INT_VALUE -> {
                variable.variableType = VariableType.BIG_INT;
                variable.value = value.value;
                return variable;
            }
        }

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
