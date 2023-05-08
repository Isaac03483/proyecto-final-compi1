package com.mio.typeSecure.models.instructions;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable implements Cloneable{

    private List<Variable> variableList;

    private SymbolTable parent;
    private static List<Function> functions;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        variableList = new ArrayList<>();
    }

    public SymbolTable() {
        variableList = new ArrayList<>();
        functions = new ArrayList<>();
    }

    public void addVariable(Variable variable){
        this.variableList.add(variable);
    }

    public Variable findById(String id){
        SymbolTable symbolTable = this;
        Variable varInTable = null;
        while(symbolTable != null){
            varInTable = symbolTable.variableList.stream().filter(variable -> variable.id.equals(id)).findFirst().orElse(null);
            if(varInTable != null){
                return varInTable;
            }
            symbolTable = symbolTable.parent;
        }

        return null;
    }

    public boolean idInTable(String id){
        return this.variableList.stream().anyMatch(variable -> variable.id.equals(id));
    }

    public void addFunction(Function function){
        functions.add(function);
    }

    public Function findFunById(String id){
        return functions.stream().filter(function -> function.id.equals(id)).findFirst().orElse(null);
    }

    public boolean funInTable(String id){
        return functions.stream().anyMatch(function -> function.id.equals(id));
    }


    @Override
    public SymbolTable clone() {
        try {
            return (SymbolTable) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
