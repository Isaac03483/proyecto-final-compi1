package com.mio.typeSecure.instructions;

import java.util.ArrayList;
import java.util.List;

public class SymbolTable {

    private List<Variable> variableList;

    private  SymbolTable parent;

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


}
