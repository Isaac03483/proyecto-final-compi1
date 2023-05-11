package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.instructions.*;
import com.mio.typeSecure.models.helpers.OperationHelper;

import java.util.List;
import java.util.Objects;

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
        Variable value = (Variable) cast.value.accept(this);
        if(value == null){
            this.errorList.add(
                    new TSError(
                            cast.line,
                            cast.column,
                            "No se pudo realizar la Conversión."
                    )
            );
            return null;
        }

        Variable result = new Variable();

        switch (cast.type){
            case CAST_NUM -> {
                if(value.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    cast.line,
                                    cast.column,
                                    "No se puede castear un valor undefined."
                            )
                    );
                    return null;
                }

                result.variableType = VariableType.NUMBER;

                switch (value.variableType){
                    case NUMBER -> {
                        result.value = value.value;
                        return result;
                    }
                    case BIG_INT -> {
                        result.value = value.value.substring(0, value.value.length()-1) ;
                        return result;
                    }
                    case BOOLEAN -> {
                        if(value.value.equalsIgnoreCase("true")){
                            result.value = "1";
                            return result;
                        }

                        result.value = "0";
                        return result;
                    }
                    case STRING -> {
                        if(value.value.isBlank()){
                            result.value = "0";
                            return result;
                        }

                        try{
                            double resultVal = Double.parseDouble(value.value.trim());
                            result.value = String.valueOf(resultVal);
                            return result;
                        } catch (NumberFormatException e){
                            this.errorList.add(
                                    new TSError(cast.line, cast.column, "No se pudo castear: "+value.value)
                            );
                            return null;
                        }
                    }
                }
            }
            case CAST_BIG -> {

                if(value.value.equals("undefined")){
                    this.errorList.add(
                            new TSError(
                                    cast.line,
                                    cast.column,
                                    "No se puede castear un valor undefined."
                            )
                    );
                    return null;
                }

                result.variableType = VariableType.BIG_INT;

                switch (value.variableType){
                    case NUMBER -> {
                        double val = Double.parseDouble(value.value);
                        if(val % 1 != 0){
                            this.errorList.add(
                                    new TSError(cast.line,
                                            cast.column,
                                            "Imposible castear por falta de presición: "+val
                                    )
                            );
                            return null;
                        }

                        int intVal = (int) val;
                        result.value = String.valueOf(intVal).concat("n");
                        return result;
                    }
                    case BIG_INT -> {
                        result.value = value.value;
                        return result;
                    }
                    case BOOLEAN -> {
                        if(value.value.equalsIgnoreCase("true")){
                            result.value = "1n";
                            return result;
                        }

                        result.value = "0n";
                        return result;
                    }
                    case STRING -> {
                        if(value.value.isBlank()){
                            result.value = "0n";
                            return result;
                        }

                        try{

                            String castVal = value.value.trim();
                            int resultVal = Integer.parseInt(castVal.substring(0,castVal.length()-1));
                            result.value = String.valueOf(resultVal).concat("n");
                            return result;
                        } catch (NumberFormatException e){
                            this.errorList.add(
                                    new TSError(cast.line, cast.column, "No se pudo castear: "+value.value)
                            );
                            return null;
                        }
                    }
                }
            }
            case CAST_BOOL -> {
                result.variableType = VariableType.BOOLEAN;
                if(value.value.equals("undefined")){
                    result.value = "false";

                }

                switch (value.variableType){
                    case NUMBER -> {
                        if(value.value.equals("0") || value.value.equals("-0.0")){
                            result.value = "false";
                            return result;
                        }

                        result.value = "true";
                        return result;
                    }

                    case BIG_INT -> {
                        if(value.value.equals("0n") || value.value.equals("-0n")){
                            result.value = "false";
                            return result;
                        }

                        result.value = "true";
                        return result;
                    }

                    case BOOLEAN -> {
                        result.value = value.value;
                        return result;
                    }

                    case STRING -> {
                        if(value.value.length() == 0){
                            result.value = "false";
                            return result;
                        }

                        result.value = "true";
                        return result;
                    }
                }

            }
            case CAST_STRING -> {
                result.variableType = VariableType.STRING;
                result.value = value.value;
                return result;
            }
        }

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

        Variable result = new Variable();
        result.variableType = VariableType.NUMBER;
        result.declarationType = DeclarationType.LET;
        switch (mathInstruction.mathType){
            case E -> {
                result.declarationType = DeclarationType.CONST;

                result.value = String.valueOf(Math.E);
                return result;
            }
            case PI -> {
                result.declarationType = DeclarationType.CONST;

                result.value = String.valueOf(Math.PI);
                return result;
            }

            case SQRT_TWO -> {
                result.declarationType = DeclarationType.CONST;

                result.value = String.valueOf(Math.sqrt(2));
                return result;
            }

            case ABS -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {
                    double resVal = Math.abs(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }

            }

            case CEIL -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {
                    double resVal = Math.ceil(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case COS -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {
                    double resVal = Math.cos(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case EXP -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {
                    double resVal = Math.exp(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case FLOOR -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {
                    double resVal = Math.floor(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case POW -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 2){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {

                    double resVal = Math.pow(Double.parseDouble(parameters.get(0).value),
                            Double.parseDouble(parameters.get(1).value)
                    );
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case RANDOM -> {
                double randomVal = Math.random();
                result.value = String.valueOf(randomVal);
                return result;
            }

            case SIN -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {

                    double resVal = Math.sin(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case SQRT -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {

                    double resVal = Math.sqrt(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

            case TAN -> {
                if(mathInstruction.instructions == null){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se necesitan parámetros para ejecutar esta instrucción."
                            )
                    );
                    return null;
                }

                List<Variable> parameters = mathInstruction.instructions.stream()
                        .map(instruction -> instruction.accept(this))
                        .filter(Objects::nonNull).map(o -> (Variable)o).toList();

                boolean isNoNumber = parameters.stream()
                        .anyMatch(parameter -> parameter.variableType != VariableType.NUMBER);

                if(isNoNumber){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro tipo NUMBER."
                            )
                    );
                    return null;
                }

                if(parameters.size() != 1){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un parámetro."
                            )
                    );
                    return null;
                }

                try {

                    double resVal = Math.tan(Double.parseDouble(parameters.get(0).value));
                    result.value = String.valueOf(resVal);
                    return result;
                } catch (NumberFormatException e){
                    this.errorList.add(
                            new TSError(
                                    mathInstruction.line,
                                    mathInstruction.column,
                                    "Se esperaba un valor tipo NUMBER."
                            )
                    );
                    return null;
                }
            }

        }


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

                if(right.variableType != VariableType.BOOLEAN){
                    this.errorList.add(
                            new TSError(
                                    unaryOperation.line,
                                    unaryOperation.column,
                                    "Los operadores lógicos solo son aplicables a tipos BOOLEAN."
                            )
                    );
                    return null;
                }

                OperationHelper.not(right.value, result);
                return result;



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
