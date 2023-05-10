package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.instructions.*;
import com.mio.typeSecure.utils.OperationHelper;

import java.util.List;

public class Debugger extends Visitor{

    private List<TSError> errorList;

    public Debugger(SymbolTable table, List<TSError> errorList) {
        super(table);
        this.errorList = errorList;
    }

    @Override
    public Variable visit(Assignment assignment) {

        Variable varInTable = this.table.findById(assignment.id);

        if(varInTable == null){
            this.errorList.add(
                    new TSError(
                            assignment.line,
                            assignment.column,
                            "No se ha encontrado la variable: "+assignment.id
                    )
            );

            return null;
        }

        if(varInTable.declarationType == DeclarationType.CONST){
            this.errorList.add(
                    new TSError(
                            assignment.line,
                            assignment.column,
                            "No puede cambiar el valor de una constante."
                    )
            );

            return null;
        }

        Variable operation = (Variable) assignment.operation.accept(this);
        if(operation == null){
            this.errorList.add(
                    new TSError(
                            assignment.line,
                            assignment.column,
                            "No se ha realizado la instrucción."
                    )
            );

            return null;
        }

        if(varInTable.variableType != operation.variableType){
            this.errorList.add(
                    new TSError(
                            assignment.line,
                            assignment.column,
                            "Los tipos no coinciden."
                    )
            );

            return null;
        }
        varInTable.value = operation.value;
        return varInTable;
    }

    @Override
    public Variable visit(BinaryOperation binaryOperation) {
        Variable left = (Variable)binaryOperation.leftOperation.accept(this);
        Variable right = (Variable) binaryOperation.rightOperation.accept(this);

        if(left == null || right == null){
            this.errorList.add(
                    new TSError(
                            binaryOperation.line,
                            binaryOperation.column,
                            "No se pudo realizar la operación"
                    )
            );
            return null;
        }

        Variable result = new Variable();

        switch (binaryOperation.operationType){
            case PLUS -> {

                if(left.variableType == VariableType.STRING || right.variableType == VariableType.STRING){
                    OperationHelper.concat(left.value, right.value, result);
                    return result;
                }

                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER ,BIG_INT -> {
                        OperationHelper.plus(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case MINUS -> {

                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.minus(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case TIMES -> {

                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER, BIG_INT -> {
                        OperationHelper.times(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case DIVIDE -> {

                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER, BIG_INT -> {
                        OperationHelper.divide(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case MOD -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.mod(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case AND -> {

                if(left.variableType != VariableType.BOOLEAN || right.variableType != VariableType.BOOLEAN){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los operadores lógicos solo son aplicables a tipos BOOLEAN."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación lógica si el valor es undefined."
                            )
                    );
                    return null;
                }

                OperationHelper.and(left.value, right.value, result);
                return result;

            }

            case OR -> {
                if(left.variableType != VariableType.BOOLEAN || right.variableType != VariableType.BOOLEAN){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los operadores lógicos solo son aplicables a tipos BOOLEAN."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación lógica si el valor es undefined."
                            )
                    );
                    return null;
                }

                OperationHelper.or(left.value, right.value, result);
                return result;
            }

            case EQUALS -> {

                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                OperationHelper.equals(left.value, right.value, result);
                return result;
            }

            case NOT_EQUALS -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                OperationHelper.notEQ(left.value, right.value, result);
                return result;
            }

            case LESS -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.less(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones '<' con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case GREATER -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.greater(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones '<' con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case LESS_EQ -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.lessEQ(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones '<' con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case GREATER_EQ -> {
                if(left.variableType != right.variableType){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "Los tipos no coinciden."
                            )
                    );
                    return null;
                }

                if(left.value.equals("undefined") || right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    binaryOperation.line,
                                    binaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (left.variableType){
                    case NUMBER,BIG_INT -> {
                        OperationHelper.greaterEQ(left.value,  right.value, result, left.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        binaryOperation.line,
                                        binaryOperation.column,
                                        "No puede realizar operaciones '<' con tipos: "+left.variableType
                                )
                        );
                        return null;
                    }
                }
            }
        }

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
        consoleLog.instructions.forEach(instruction -> {
            Variable result = (Variable) instruction.accept(this);
            if(result == null){
                this.errorList.add(
                        new TSError(
                                consoleLog.line,
                                consoleLog.column,
                                "No se pudo realizar la Instrucción."
                        )
                );
            }

        });

    }

    @Override
    public void visit(Continue continueInstruction) {

    }

    @Override
    public void visit(Declaration declaration) {
        declaration.instructions.forEach(instruction -> {

            Variable var = (Variable)instruction.accept(this);

            if(var == null){
                this.errorList.add(new TSError(
                        declaration.line,
                        declaration.column,
                        "No se pudo declarar la variable.")
                );

                return;
            }

            if(declaration.type == DeclarationType.CONST && var.value.equals("undefined")){
                this.errorList.add(new TSError(
                        declaration.line,
                        declaration.column,
                        "Debe asignar un valor al declarar una constante.")
                );

                return;
            }


            var.declarationType = declaration.type;
            boolean exist = table.idInTable(var.id);

            if(exist){
                this.errorList.add(new TSError(declaration.line, declaration.column, "Variable declarada."));
                return;
            }

            table.addVariable(var);

        });
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
        Variable right = (Variable) unaryOperation.rightInstruction.accept(this);

        if(right == null){
            this.errorList.add(
                    new TSError(
                            unaryOperation.line,
                            unaryOperation.column,
                            "No se pudo realizar la operación"
                    )
            );
            return null;
        }

        Variable result = new Variable();

        switch (unaryOperation.operationType){
            case INCREMENT -> {
                if(right.declarationType == DeclarationType.CONST){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "No puede alterar el valor de una constante."
                            )
                    );
                    return null;
                }

                if(right.variableType != VariableType.NUMBER && right.variableType != VariableType.BIG_INT){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "No puede incrementar una variable que no sea de tipo NUMBER o BIGINT."
                            )
                    );
                    return null;
                }
                OperationHelper.increment(right);

                Variable varInTable = this.table.findById(right.id);
                varInTable.value = right.value;
                return right;

            }
            case DECREMENT -> {
                if(right.declarationType == DeclarationType.CONST){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "No puede alterar el valor de una constante."
                            )
                    );
                    return null;
                }

                if(right.variableType != VariableType.NUMBER && right.variableType != VariableType.BIG_INT){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "No puede decrementar una variable que no sea de tipo NUMBER o BIGINT."
                            )
                    );
                    return null;
                }

                OperationHelper.decrement(right);
                Variable varInTable = this.table.findById(right.id);
                varInTable.value = right.value;
                return right;
            }


            case MINUS -> {

                if(right.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "No se puede realizar la operación si el valor es undefined."
                            )
                    );
                    return null;
                }

                switch (right.variableType){
                    case NUMBER, BIG_INT -> {
                        OperationHelper.times("-1",  right.value, result, right.variableType);
                        return result;
                    }
                    default -> {
                        this.errorList.add(
                                new TSError(
                                        unaryOperation.line,
                                        unaryOperation.column,
                                        "No puede realizar operaciones aritméticas con tipos: "+right.variableType
                                )
                        );
                        return null;
                    }
                }
            }

            case NOT -> {

            }
        }

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
        if(variableDeclaration.variableType == null && variableDeclaration.instruction == null){
            this.errorList.add(
                    new TSError(
                            variableDeclaration.line,
                            variableDeclaration.column,
                            "Se necesita el tipo o el valor de la variable como mínimo para declararla."
                    )
            );
            return null;
        }

        Variable result = new Variable();
        result.id = variableDeclaration.id;
        result.variableType = variableDeclaration.variableType;

        if(variableDeclaration.instruction == null){
            return result;
        }

        Variable operation = (Variable) variableDeclaration.instruction.accept(this);

        if(operation == null){
            this.errorList.add(
                    new TSError(
                            variableDeclaration.line,
                            variableDeclaration.column,
                            "No se pudo realizar la instrucción."
                    )
            );
            return null;
        }

        result.value = operation.value;

        if(result.variableType == null){
            result.variableType = operation.variableType;
        }

        if(variableDeclaration.variableType != null && operation.variableType != result.variableType){
            this.errorList.add(
                    new TSError(
                            variableDeclaration.line,
                            variableDeclaration.column,
                            "Los tipos no coinciden."
                    )
            );
            return null;
        }


        return result;
    }

    @Override
    public Variable visit(While whileInstruction) {
        return null;
    }
}
