package com.mio.typeSecure.instructions;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable implements Cloneable{

    private List<Variable> variableList;

    private  SymbolTable parent;
    private static List<Function> functions;

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        variableList = new ArrayList<>();
    }

    public SymbolTable() {
        variableList = new ArrayList<>();
    }

    public void addVariable(Variable variable){
        this.variableList.add(variable);
    }

    public Variable findById(String id){
        return this.variableList.stream().filter(variable -> variable.id.equals(id)).findFirst().orElse(null);
    }

    public boolean idInTable(String id){
        return this.variableList.stream().anyMatch(variable -> variable.id.equals(id));
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
