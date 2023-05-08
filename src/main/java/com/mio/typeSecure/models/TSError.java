package com.mio.typeSecure.models;

public record TSError(int line, int column, String message) {

    @Override
    public String toString() {
        return "Linea: "+line+" Columna: "+column+". "+message+".";
    }
}
