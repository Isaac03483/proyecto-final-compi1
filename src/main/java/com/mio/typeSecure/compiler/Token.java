package com.mio.typeSecure.compiler;

public record Token(int type, String value, int line, int column) {

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
