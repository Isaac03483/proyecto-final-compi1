package com.mio.typeSecure.instructions;

import java.util.Objects;

public record Parameter(String id, VariableType variableType) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (!Objects.equals(id, parameter.id)) return false;
        return variableType == parameter.variableType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (variableType != null ? variableType.hashCode() : 0);
        return result;
    }
}
