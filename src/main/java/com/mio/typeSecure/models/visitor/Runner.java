package com.mio.typeSecure.models.visitor;

import com.mio.typeSecure.models.TSError;
import com.mio.typeSecure.models.helpers.OperationHelper;
import com.mio.typeSecure.models.helpers.ReportHelper;
import com.mio.typeSecure.models.instructions.*;
import com.mio.typeSecure.models.symbolTable.ScopeType;
import com.mio.typeSecure.models.symbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        if(left.variableType == VariableType.VOID || right.variableType == VariableType.VOID){
            this.errorList.add(
                    new TSError(
                            binaryOperation.line,
                            binaryOperation.column,
                            "No se pudo realizar la operación."
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

                OperationHelper.equals(left.value, right.value, result, left.variableType);
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

                OperationHelper.notEQ(left.value, right.value, result, left.variableType);
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
    public Break visit(Break breakInstruction) {
        System.out.println("EVALUANDO SCOPE EN BREAK RUNNER: "+this.table.scopeType);
        if(!this.table.isInLoopScope()){
            System.out.println(":::ERROR EN BREAK::::");
            this.errorList.add(
                    new TSError(breakInstruction.line,
                            breakInstruction.column,
                            "break solo puede ser usado dentro de un ciclo.")
            );
            return null;
        }

        System.out.println("BREAK: SALIENDO DE SCOPE: "+this.table.scopeType+" ENTRANDO A: "+this.table.parent.scopeType);

        this.table = this.table.parent;
        return breakInstruction;
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
        switch (callFunction.id){
            case "getSymbolTable" -> {
                if(this.table == null){
                    this.errorList.add(
                            new TSError(
                                    callFunction.line,
                                    callFunction.column,
                                    "Algo salió mal al crear el HTML."
                            )
                    );
                    return null;
                }

                ReportHelper.createTableReport(this.table);
            }

            case "printAst" -> {

            }

            default -> {
                List<Variable> parameters = new ArrayList<>();

                if(callFunction.instructions != null){
                    parameters = callFunction.instructions.stream()
                            .map(instruction -> instruction.accept(this)).map(parameter -> {
                                if(parameter == null){
                                    this.errorList.add(
                                            new TSError(callFunction.line,
                                                    callFunction.column,
                                                    "No se pudo realizar la operación.")
                                    );
                                    return null;
                                }
                                return (Variable) parameter;
                            }).filter(Objects::nonNull).toList();
                }

                List<Function> functions = this.table.findFunById(callFunction.id);
                if(functions.isEmpty()){
                    this.errorList.add(
                            new TSError(callFunction.line,
                                    callFunction.column,
                                    "No se encontró la función")
                    );
                    return null;
                }

                Function fun = getFun(functions, callFunction.id, parameters);

                if(fun == null){
                    this.errorList.add(
                            new TSError(callFunction.line,
                                    callFunction.column,
                                    "No se encontró la función")
                    );
                    return null;
                }

                SymbolTable originalTable = this.table;
                System.out.println("GUARDANDO SCOPE ORIGINAL");
                this.table = new SymbolTable(ScopeType.FUN_SCOPE, fun.parentTable);
                Variable  returnVar = new Variable();
                if(fun.parametersInstr != null){
                    for(int i = 0; i < fun.parametersInstr.size(); i++){
                        Variable paramVar = (Variable) fun.parametersInstr.get(i).accept(this);
                        if(paramVar != null){
                            paramVar.value = parameters.get(i).value;
                        }
                    }
                }


                System.out.println("COMENZANDO ANÁLISIS DE INSTRUCCIONES");
                for(Instruction funIn: fun.instructions){
                    if(funIn instanceof If ifIn){
                        Object o = ifIn.accept(this);
                        if(o instanceof Variable var){
                            this.table = originalTable;
                            return var;
                        }
                    } else if(funIn instanceof DoWhile doWhile){
                        Variable var = doWhile.accept(this);
                        System.out.println("RECIBIENDO VARIABLE DESDE DO-WHILE: "+var);
                        if(var != null){
                            this.table = originalTable;

                            return var;
                        }
                    } else if (funIn instanceof For forIn) {
                        Variable var = forIn.accept(this);
                        System.out.println(var);
                        if(var != null){
                            System.out.println("ENCONTRADO RETURN DE FOR");
                            this.table = originalTable;
                            return var;
                        }
                    } else if(funIn instanceof While whileIn){
                        Variable var = whileIn.accept(this);
                        if(var != null){
                            this.table = originalTable;

                            return var;
                        }
                    } else if(funIn instanceof ReturnInstruction returnIn){
                        Variable var = returnIn.accept(this);
                        if(var != null){
                            this.table = originalTable;
                            return var;
                        }

                    } else {
                        funIn.accept(this);
                    }
                }


                this.table = originalTable;
                return returnVar;
            }
        }
        return null;
    }

    @Override
    public void visit(ConsoleLog consoleLog) {
        StringBuilder content = new StringBuilder();
        consoleLog.instructions.forEach(instruction -> {
            Variable result = (Variable) instruction.accept(this);
            if(result == null){
                System.out.println("el resultado de console es null");
                this.errorList.add(
                        new TSError(
                                consoleLog.line,
                                consoleLog.column,
                                "No se pudo realizar la Instrucción."
                        )
                );
                return;
            }

            if (result.variableType == VariableType.VOID) {
                System.out.println("el resultado de console es null");

                this.errorList.add(
                        new TSError(
                                consoleLog.line,
                                consoleLog.column,
                                "No se pudo realizar la Instrucción."
                        )
                );
                return;

            }

            content.append(result.value);
        });
        out.add(content.toString());
    }


    @Override
    public Continue visit(Continue continueInstruction) {
        System.out.println("EVALUANDO SCOPE EN CONTINUE RUNNER: "+this.table.scopeType);

        if(!this.table.isInLoopScope()){
            System.out.println("::::ERROR EN CONTINUE::::");
            this.errorList.add(
                    new TSError(continueInstruction.line,
                            continueInstruction.column,
                            "Continue solo puede ser usado dentro de un ciclo.")
            );
            return null;
        }

        System.out.println("CONTINUE: SALIENDO DE SCOPE: "+this.table.scopeType+" ENTRANDO A: "+this.table.parent.scopeType);
        this.table = this.table.parent;
        return continueInstruction;
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
            var.line = declaration.line;
            var.column = declaration.column;
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
        this.table = new SymbolTable(ScopeType.LOOP_SCOPE,this.table);

        Variable operation = (Variable) doWhile.operation.accept(this);

        if(operation == null){
            this.errorList.add(
                    new TSError(
                            doWhile.line,
                            doWhile.column,
                            "No se pudo realizar la operación."
                    )
            );
            return null;
        }

        if(operation.variableType != VariableType.BOOLEAN){
            this.errorList.add(
                    new TSError(
                            doWhile.line,
                            doWhile.column,
                            "La operación necesita ser de tipo BOOLEAN."
                    )
            );
            return null;
        }

        doWhile.instructions.forEach(instruction -> instruction.accept(this));
        boolean booleanOperation = Boolean.parseBoolean(operation.value);

        while(booleanOperation){
//            doWhile.instructions.forEach(instruction -> instruction.accept(this));

            for(Instruction instruction: doWhile.instructions){
                if(instruction instanceof If ifIn){
                    Object object = ifIn.accept(this);

                    if(object != null){
                        if(object instanceof Break breakIn){
                            return null;
                        } else if(object instanceof Continue continueIn){
                            System.out.println("Encontrando continue en if");
                            break;
                        }else if(object instanceof Variable variable){
                            System.out.println("RECIBIENDO VAR DESDE IF EN DO-WHILE");
                            if(this.table.parent == null){
                                System.out.println("DO WHILE NO HAY PARENT");
                                this.errorList.add(
                                        new TSError(doWhile.line,
                                                doWhile.column,
                                                "No se encuentra dentro de una función.")
                                );
                                return null;
                            }

                            System.out.println("REENVIANDO RETURN DESDE DO-WHILE CON VALOR: "+variable);
                            this.table = this.table.parent;
                            return variable;
                        }
                    }

                } else if (instruction instanceof For forIn) {
                    Variable variable = forIn.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(doWhile.line,
                                        doWhile.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof While whileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = whileIn.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(doWhile.line,
                                        doWhile.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof DoWhile doWhileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = doWhileIn.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(doWhile.line,
                                        doWhile.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof Break breakIn){
                    return null;
                } else if (instruction instanceof Continue continueIn){
                    System.out.println("Encontrando un continue.");
                    break;
                }else if(instruction instanceof ReturnInstruction returnInstruction){

                    Variable variable = returnInstruction.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(doWhile.line,
                                        doWhile.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                } else {
                    instruction.accept(this);
                }
            }


            operation = (Variable) doWhile.operation.accept(this);
            booleanOperation = Boolean.parseBoolean(operation.value);
        }


        this.table = this.table.parent;
        return null;
    }

    @Override
    public Object visit(Else elseInstruction) {

        for(Instruction instruction: elseInstruction.instructions){
            if(instruction instanceof If ifIn){
                Object object = ifIn.accept(this);

                if(object == null){
                    continue;
                }

                if(object instanceof Break breakIn){
                    return breakIn.accept(this);
                } else if(object instanceof Continue continueIn){
                    return continueIn.accept(this);
                } else if(object instanceof Variable variable){
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(elseInstruction.line,
                                        elseInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }
                    return variable;
                }

            } else if(instruction instanceof For forIn){
                Variable variable = forIn.accept(this);
                if(this.table.parent == null){
                    this.errorList.add(
                            new TSError(elseInstruction.line,
                                    elseInstruction.column,
                                    "No se encuentra dentro de una función.")
                    );
                    return null;
                }

                if(variable != null){
                    return variable;
                }
            }else if(instruction instanceof While whileIn){
                //TODO: verificar que lo retornado sea una variable (return)
                Variable variable = whileIn.accept(this);
                if(this.table.parent == null){
                    this.errorList.add(
                            new TSError(elseInstruction.line,
                                    elseInstruction.column,
                                    "No se encuentra dentro de una función.")
                    );
                    return null;
                }

                if(variable != null){
                    return variable;
                }
            } else if(instruction instanceof DoWhile doWhileIn){
                //TODO: verificar que lo retornado sea una variable (return)
                Variable variable = doWhileIn.accept(this);
                if(this.table.parent == null){
                    this.errorList.add(
                            new TSError(elseInstruction.line,
                                    elseInstruction.column,
                                    "No se encuentra dentro de una función.")
                    );
                    return null;
                }

                if(variable != null){
                    return variable;
                }
            } else if(instruction instanceof Break breakIn){
                return breakIn.accept(this);
            } else if (instruction instanceof Continue continueIn){
                return continueIn.accept(this);
            } else if (instruction instanceof ReturnInstruction returnIn){
                Variable variable = returnIn.accept(this);
                if(variable != null){
                    return variable;
                }
            }else {
                instruction.accept(this);
            }
        }
        return null;
    }

    @Override
    public Variable visit(For forInstruction) {
        this.table = new SymbolTable(ScopeType.LOOP_SCOPE,this.table);

        forInstruction.assignmentBlock.accept(this);

        Variable operation = (Variable) forInstruction.operationBlock.accept(this);

        if(operation == null){
            this.errorList.add(
                    new TSError(forInstruction.line,
                            forInstruction.column,
                            "No se pudo realizar la operación.")
            );
            return null;
        }

        if(operation.variableType != VariableType.BOOLEAN){
            this.errorList.add(
                    new TSError(forInstruction.line,
                            forInstruction.column,
                            "La operación debe ser de tipo BOOLEAN.")
            );
            return null;
        }

        boolean booleanOperation = Boolean.parseBoolean(operation.value);

        while (booleanOperation){

//            forInstruction.instructions.forEach(instruction -> instruction.accept(this));
            for(Instruction instruction: forInstruction.instructions){
                if(instruction instanceof If ifIn){
                    Object object = ifIn.accept(this);

                    if(object != null){
                        if(object instanceof Break breakIn){
                            return null;
                        } else if(object instanceof Continue continueIn){
                            System.out.println("Encontrando continue en if");
                            break;
                        } else if(object instanceof Variable variable){
                            System.out.println("RECIBIENDO VARIABLE DE IF");
                            if(this.table.parent == null){
                                System.out.println("su padre es null.");
                                this.errorList.add(
                                        new TSError(forInstruction.line,
                                                forInstruction.column,
                                                "No se encuentra dentro de una función.")
                                );
                                return null;
                            }

                            System.out.println(variable);
                            this.table = this.table.parent;
                            return variable;
                        }
                    }

                } else if(instruction instanceof For forIn){
                    Variable variable = forIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(forInstruction.line,
                                        forInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                }else if(instruction instanceof While whileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = whileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(forInstruction.line,
                                        forInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof DoWhile doWhileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = doWhileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(forInstruction.line,
                                        forInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                }else if(instruction instanceof Break breakIn){
                    return null;
                } else if (instruction instanceof Continue continueIn){
                    System.out.println("Encontrando un continue.");
                    break;
                } else if(instruction instanceof ReturnInstruction returnInstruction){

                    Variable variable = returnInstruction.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(forInstruction.line,
                                        forInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                }else {
                    instruction.accept(this);
                }
            }

            forInstruction.incrementBlock.accept(this);
            operation = (Variable) forInstruction.operationBlock.accept(this);
            booleanOperation = Boolean.parseBoolean(operation.value);

        }

        this.table = this.table.parent;
        return null;
    }

    @Override
    public void visit(Function function) {
        List<Function> functions = this.table.findFunById(function.id);

        if(!functions.isEmpty()){
            for(Function funInTable: functions){
                boolean same = isFunEQ(function, funInTable);

                if(same){
                    this.errorList.add(
                            new TSError(function.line,
                                    function.column,
                                    "Ya se ha declarado una función con mismo nombre y atributos.")
                    );
                    return;
                }
            }
        }

        this.table = new SymbolTable(ScopeType.FUN_SCOPE,this.table);

        function.parentTable = this.table.parent.clone();
        if(function.parametersInstr != null){
            for(Instruction parameter: function.parametersInstr){
                Variable result = (Variable) parameter.accept(this);
                if(result == null){
                    this.errorList.add(
                            new TSError(function.line,
                                    function.column,
                                    "No se pudo crear la variable.")
                    );
                    return;
                }
            }
        }

        List<Variable> variables = getAllReturn(function);

        if(variables.isEmpty()){

            if(function.returnType == null){
                function.returnType = ReturnType.VOID;
                this.table = table.parent;
                this.table.addFunction(function);
                return;
            }

            if(function.returnType != ReturnType.VOID){
                this.errorList.add(
                        new TSError(function.line,
                                function.column,
                                "Los valores de retorno no coinciden con el tipo de función.")
                );
                return;
            }
        } else {
            Variable firstReturn = variables.get(0);
            boolean hasDifferentType = variables.stream().anyMatch(var -> var.variableType != firstReturn.variableType);
            if(hasDifferentType){
                this.errorList.add(
                        new TSError(function.line,
                                function.column,
                                "Los valores de retorno no coinciden.")
                );
                return;
            }

            if(function.returnType == null){
                switch (firstReturn.variableType){
                    case STRING -> function.returnType = ReturnType.STRING;
                    case NUMBER -> function.returnType = ReturnType.NUMBER;
                    case BIG_INT -> function.returnType = ReturnType.BIG_INT;
                    case BOOLEAN -> function.returnType = ReturnType.BOOLEAN;
                }
                this.table = table.parent;
                this.table.addFunction(function);
                return;
            }

            if(!function.returnType.toString().equals(firstReturn.variableType.toString())){
                this.errorList.add(
                        new TSError(function.line,
                                function.column,
                                "Los valores de retorno no coinciden con el tipo de función.")
                );
                return;
            }

            if(function.returnType != ReturnType.VOID){
                if(!(function.instructions.get(function.instructions.size()-1) instanceof ReturnInstruction)){
                    this.errorList.add(
                            new TSError(function.line,
                                    function.column,
                                    "Se esperaba un valor de retorno al final de la función.")
                    );
                    return;
                }
            }

        }



        this.table = table.parent;
        this.table.addFunction(function);
    }

    @Override
    public Object visit(If ifInstruction) {
        Variable operation = (Variable) ifInstruction.operation.accept(this);
        if(operation == null){
            this.errorList.add(
                    new TSError(
                            ifInstruction.line,
                            ifInstruction.column,
                            "No se pudo realizar la comparación."
                    )
            );
            return null;
        }

        if(operation.value.equals("true")){
            this.table = new SymbolTable(ScopeType.IF_SCOPE, this.table);

//            ifInstruction.trueBlock.forEach(instruction -> instruction.accept(this));
            for (Instruction instruction : ifInstruction.trueBlock) {
                if(instruction instanceof If ifIn){
                    Object object = ifIn.accept(this);

                    if(object == null){
                        continue;
                    }

                    if(object instanceof Break breakIn){
                        return breakIn.accept(this);
                    } else if(object instanceof Continue continueIn){
                        return continueIn.accept(this);
                    } else if(object instanceof Variable variable){
                        if(this.table.parent == null){
                            this.errorList.add(
                                    new TSError(ifInstruction.line,
                                            ifInstruction.column,
                                            "No se encuentra dentro de una función.")
                            );
                            return null;
                        }

                        this.table = this.table.parent;
                        return variable;
                    }

                } else if(instruction instanceof For forIn){
                    Variable variable = forIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(ifInstruction.line,
                                        ifInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                }  else if(instruction instanceof While whileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = whileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(ifInstruction.line,
                                        ifInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof DoWhile doWhileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = doWhileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(ifInstruction.line,
                                        ifInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof Break breakIn){

                    return breakIn.accept(this);
                } else if (instruction instanceof Continue continueIn){
                    return continueIn.accept(this);
                } else if(instruction instanceof ReturnInstruction returnInstruction){
                    Variable variable = returnInstruction.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(ifInstruction.line,
                                        ifInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                }else {
                    instruction.accept(this);
                }

            }
            this.table = this.table.parent;
            return null;
        }
        if(ifInstruction.falseBlock != null){

            this.table = new SymbolTable(ScopeType.IF_SCOPE, this.table);
            Object object = ifInstruction.falseBlock.accept(this);

            if(object instanceof Break breakIn){
                return breakIn;
            } else if(object instanceof Continue continueIn){
                return continueIn;
            } else if(object instanceof Variable variable){
                if(this.table.parent == null){
                    this.errorList.add(
                            new TSError(ifInstruction.line,
                                    ifInstruction.column,
                                    "No puede retornar un valor si no está en una función.")
                    );
                    return null;
                }

                this.table = this.table.parent;
                return variable;
            }
            this.table = this.table.parent;
            return null;
        }

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
    public Variable visit(Parameter parameter){

        boolean isInTable = this.table.idInTable(parameter.id);

        if(isInTable){
            this.errorList.add(
                    new TSError(parameter.line,
                            parameter.column,
                            parameter.id+" ya se encuentra definida.")
            );
            return null;
        }

        Variable parameterVar = new Variable();
        parameterVar.id = parameter.id;
        parameterVar.variableType = parameter.variableType;
        parameterVar.declarationType = DeclarationType.LET;
        switch (parameterVar.variableType){
            case NUMBER,STRING -> parameterVar.value = "1";
            case BIG_INT -> parameterVar.value = "10n";
            case BOOLEAN -> parameterVar.value = "true";
        }

        this.table.addVariable(parameterVar);

        return parameterVar;
    }


    @Override
    public Variable visit(ReturnInstruction returnInstruction) {
        if(this.table.parent == null){
            this.errorList.add(
                    new TSError(returnInstruction.line,
                            returnInstruction.column,
                            "Return solo puede ser usado dentro de una función.")
            );
            return null;
        }
        Variable value = new Variable();
        value.line = returnInstruction.line;
        value.column = returnInstruction.column;
        if(returnInstruction.value != null){
            value = (Variable) returnInstruction.value.accept(this);
            if(value == null){
                this.errorList.add(
                        new TSError(returnInstruction.line,
                                returnInstruction.column,
                                "No se pudo realizar la operación.")
                );
                return null;
            }
//            System.out.println("TIPO DE VARIABLE: "+value.variableType);

            return value;
        }

        value.variableType = VariableType.VOID;
        value.declarationType = DeclarationType.LET;
        value.id = "void";

        return value;
    }

    @Override
    public Variable visit(StringInstruction stringInstruction) {
        Variable value = (Variable) stringInstruction.value.accept(this);

        if(value == null){
            this.errorList.add(
                    new TSError(stringInstruction.line,
                            stringInstruction.column,
                            "No se pudo realizar la operación")
            );
            return null;
        }

        if(value.variableType != VariableType.STRING){
            this.errorList.add(
                    new TSError(stringInstruction.line,
                            stringInstruction.column,
                            "Solo puede realizar este tipo de operaciones con tipos STRING.")
            );
            return null;
        }

        Variable result = new Variable();
        switch (stringInstruction.type){
            case CHAR_AT -> {
                try{
                    Variable indexVar = (Variable) stringInstruction.instruction.accept(this);
                    if(indexVar.variableType != VariableType.NUMBER){
                        this.errorList.add(
                                new TSError(stringInstruction.line,
                                        stringInstruction.column,
                                        "Se esperaba un parámetro tipo NUMBER.")
                        );
                        return null;
                    }
                    int index = Integer.parseInt(indexVar.value);
                    result.variableType = VariableType.STRING;
                    result.value = String.valueOf(value.value.charAt(index));
                    return result;
                } catch(IndexOutOfBoundsException e){
                    this.errorList.add(
                            new TSError(stringInstruction.line,
                                    stringInstruction.column,
                                    "Indice fuera del tamaño de la variable.")
                    );
                    return null;
                }
            }

            case CONCAT -> {
                Variable attribute = (Variable) stringInstruction.instruction.accept(this);
                if(attribute.variableType != VariableType.STRING){
                    this.errorList.add(
                            new TSError(stringInstruction.line,
                                    stringInstruction.column,
                                    "Se esperaba un valor tipo STRING.")
                    );
                    return null;
                }

                result.variableType = VariableType.STRING;
                result.value = value.value.concat(attribute.value);
                return result;
            }

            case LENGTH -> {
                result.variableType = VariableType.NUMBER;
                result.value = String.valueOf(value.value.length());
                return result;
            }

            case LOWER -> {
                result.variableType = VariableType.STRING;
                result.value = value.value.toLowerCase(Locale.ROOT);
                return result;
            }

            case UPPER -> {
                result.variableType = VariableType.STRING;
                result.value = value.value.toUpperCase(Locale.ROOT);
                return result;
            }
        }

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
        Variable operation = (Variable) whileInstruction.operation.accept(this);

        if(operation == null){
            this.errorList.add(
                    new TSError(
                            whileInstruction.line,
                            whileInstruction.column,
                            "No se pudo realizar la operación."
                    )
            );
            return null;
        }

        if(operation.variableType != VariableType.BOOLEAN){
            this.errorList.add(
                    new TSError(
                            whileInstruction.line,
                            whileInstruction.column,
                            "La operación necesita ser de tipo BOOLEAN."
                    )
            );
            return null;
        }

        this.table = new SymbolTable(ScopeType.LOOP_SCOPE, this.table);
        boolean booleanOperation = Boolean.parseBoolean(operation.value);

        while (booleanOperation) {
//            whileInstruction.instructions.forEach(instruction -> instruction.accept(this));
            System.out.println("Iterando valores de while");
            for(Instruction instruction: whileInstruction.instructions){
                if(instruction instanceof If ifIn){
                    Object object = ifIn.accept(this);

                    if(object != null){
                        if(object instanceof Break breakIn){
                            return null;
                        } else if(object instanceof Continue continueIn){
                            System.out.println("Encontrando continue en if");
                            break;
                        }else if(object instanceof Variable variable){
                            if(this.table.parent == null){
                                this.errorList.add(
                                        new TSError(whileInstruction.line,
                                                whileInstruction.column,
                                                "No se encuentra dentro de una función.")
                                );
                                return null;
                            }

                            this.table = this.table.parent;
                            return variable;
                        }
                    }

                } else if(instruction instanceof For forIn){
                    Variable variable = forIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(whileInstruction.line,
                                        whileInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                } else if(instruction instanceof While whileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = whileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(whileInstruction.line,
                                        whileInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof DoWhile doWhileIn){
                    //TODO: verificar que lo retornado sea una variable (return)
                    Variable variable = doWhileIn.accept(this);
                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(whileInstruction.line,
                                        whileInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }
                } else if(instruction instanceof Break breakIn){
                    return null;
                } else if (instruction instanceof Continue continueIn){
                    System.out.println("Encontrando un continue.");
                    break;
                } else if(instruction instanceof ReturnInstruction returnInstruction){

                    Variable variable = returnInstruction.accept(this);

                    if(this.table.parent == null){
                        this.errorList.add(
                                new TSError(whileInstruction.line,
                                        whileInstruction.column,
                                        "No se encuentra dentro de una función.")
                        );
                        return null;
                    }

                    if(variable != null){
                        this.table = this.table.parent;
                        return variable;
                    }

                } else {
                    instruction.accept(this);
                }
            }

            operation = (Variable) whileInstruction.operation.accept(this);
            booleanOperation = Boolean.parseBoolean(operation.value);
        }

        this.table = this.table.parent;
        return null;
    }

    public List<Variable> getAllReturn(Function function){
        List<Variable> resultList = new ArrayList<>();

        getAllReturn(function.instructions, resultList);

        return resultList;

    }

    public void getAllReturn(List<Instruction> instructions, List<Variable> resultList){
        for(Instruction instruction: instructions){

            if(instruction instanceof If anIf){
                getAllReturn(anIf.trueBlock, resultList);
                if(anIf.falseBlock != null){
                    if(anIf.falseBlock instanceof Else anElse){
                        getAllReturn(anElse.instructions, resultList);
                    } else if(anIf.falseBlock instanceof If ifI) {
                        getAllReturn(ifI.trueBlock, resultList);
                    }
                }
            } else if(instruction instanceof Else elseI){
                getAllReturn( elseI.instructions, resultList);
            } else if(instruction instanceof For forI){
                getAllReturn( forI.instructions, resultList);

            } else if(instruction instanceof DoWhile doWhile){
                getAllReturn( doWhile.instructions, resultList);

            } else if(instruction instanceof While whileI){
                getAllReturn( whileI.instructions, resultList);
            } else if(instruction instanceof ReturnInstruction returnI){
                Variable variable = returnI.accept(this);
                if(variable != null){
                    resultList.add(variable);
                }
            }
        }
    }

    public boolean isFunEQ(Function newFun, Function funInTable){
        if(newFun.parametersInstr == null && funInTable.parametersInstr == null){
            return true;
        }

        if(newFun.parametersInstr != null){
            if(funInTable.parametersInstr == null){
                return false;
            }

            if(newFun.parametersInstr.size() != funInTable.parametersInstr.size()){
                return false;
            }

            for(int i = 0; i < newFun.parametersInstr.size(); i++){
                Parameter p1 = (Parameter) newFun.parametersInstr.get(i);
                Parameter p2 = (Parameter) funInTable.parametersInstr.get(i);

                if(p1.variableType != p2.variableType){
                    return false;
                }

            }

            return true;
        }

        return false;

    }

    public Function getFun(List<Function> functions, String id, List<Variable> parameters){
        List<Function> functionsWithParams = functions.stream()
                .filter(function -> function.parametersInFun.size() == parameters.size()).toList();

        if(functionsWithParams.isEmpty()){
            return null;
        }

        for(Function function: functionsWithParams){

            boolean isFun = true;
            for(int i = 0; i < parameters.size(); i++){
                if(function.parametersInFun.get(i).variableType != parameters.get(i).variableType){
                    isFun = false;
                }
            }

            if(isFun){
                return function;
            }
        }

        return null;
    }

}
