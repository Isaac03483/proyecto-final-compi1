package com.mio.typeSecure.models.symbolTable;

import com.mio.typeSecure.models.instructions.Function;
import com.mio.typeSecure.models.instructions.Variable;
import java_cup.runtime.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SymbolTable implements Cloneable {

    public final List<Variable> variableList;

    public SymbolTable parent;
    public static List<Function> functions;
    public ScopeType scopeType;

    public SymbolTable(ScopeType scopeType, SymbolTable parent) {
        this.scopeType = scopeType;
        this.parent = parent;
        variableList = new ArrayList<>();
    }

    public SymbolTable(ScopeType scopeType) {
        this.scopeType = scopeType;
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
            varInTable = symbolTable.variableList.stream()
                    .filter(variable -> variable.id.equals(id))
                    .findFirst().orElse(null);

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

    public List<Function> findFunById(String id){
        return functions.stream().filter(function -> function.id.equals(id)).collect(Collectors.toList());
    }

    public boolean isInLoopScope(){
        SymbolTable currentTable = this;
        while(currentTable != null){
            System.out.println("evaluando scope: "+currentTable.scopeType);
            if(currentTable.scopeType == ScopeType.LOOP_SCOPE){
                return true;
            }
            currentTable = currentTable.parent;
        }
        return false;
    }

    public boolean isInFunScope(){
        SymbolTable currentTable = this;
        while(currentTable != null){
            if(currentTable.scopeType == ScopeType.FUN_SCOPE){
                return true;
            }
            currentTable = currentTable.parent;
        }
        return false;
    }

    public boolean funInTable(String id){
        return functions.stream().anyMatch(function -> function.id.equals(id));
    }


    public List<Variable> getVariableList(){
        return  this.variableList;
    }

    @Override
    public SymbolTable clone() {
        try {
            SymbolTable clone = (SymbolTable) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "parent=" + parent +
                ", scopeType=" + scopeType +
                '}';
    }
}
