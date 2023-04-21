package com.mio.typeSecure.compiler.lexer;

/*
compilar archivo.jflex
java -jar jflex-full-1.9.0.jar /home/mio/Escritorio/2023/proyecto-final-compi1/src/main/jflex/tsLexer.jflex
*/

import com.mio.typeSecure.compiler.Token;
import static com.mio.server.compiler.parser.TSParserSym.*;
import java_cup.runtime.Symbol;
import java.util.ArrayList;
import java.util.List;



%%

%public
%class TypeSecureLexer
%unicode
%line
%column
%type java_cup.runtime.Symbol
%cup

%{

    private Symbol symbolWithValue(int type, Object value){
        return new Symbol(type, new Token(type, value.toString(), yyline+1, yycolumn+1 ));
    }

    private Symbol symbolWithoutValue(int type){
        return new Symbol(type, new Token(type, null, yyline+1, yycolumn+1 );
    }

%}

/*
%{
    private Token symbolWithValue(int type, Object value){
        Token token = new Token(type, value.toString(), yyline+1, yycolumn+1 );
        System.out.println(token);
        return token;
    }
    private Token symbolWithoutValue(int type){
        Token token = new Token(type, null, yyline+1, yycolumn+1 );
        System.out.println(token);
        return token;
    }
%}*/

%eofval{
    return symbolWithoutValue(EOF);
%eofval}
%eofclose

ENDLINE = \r|\n|\r\n
WHITESPACE = {ENDLINE}|[ \t\f]
STRING_VALUE = (\"[^\"]*\") | (\'[^\']*\')
SUM = "+"
MINUS = "-"
TIMES = "*"
DIVIDE = "/"
MOD = "%"
LPAREN = "("
RPAREN = ")"
COLON = ":"
SEMICOLON = ";"
ASSIGN = "="
EQUALS = "=="
NOT_EQUALS = "!="
GREATER = ">"
LESS = "<"
LESS_EQ = "<="
GREATER_EQ = ">="
AND = "&&"
OR = "||"
NOT = "!"
COMMA = ","
DOT = "."
LBRACE = "{"
RBRACE = "}"
TRUE = "true"
FALSE = "false"
NUMBER = "number"
BIGINT = "bigint"
STRING = "string"
BOOLEAN = "boolean"
VOID = "void"
UNDEFINED = "undefined"
CONST = "const"
LET = "let"
NUMBER_FUN = "Number"
BIGINT_FUN = "BigInt"
STRING_FUN = "String"
BOOLEAN_FUN = "Boolean"
LENGTH = "length"
CHAR_AT = "charAt"
LOWER = "toLowerCase"
UPPER = "toUpperCase"
CONCAT = "concat"
CONSOLE_LOG = "console.log"
IF = "if"
ELSE = "else"
FOR = "for"
WHILE = "while"
DO = "do"
BREAK = "break"
CONTINUE = "continue"
FUNCTION = "function"
RETURN = "return"
E = "Math.E"
PI = "Math.PI"
SQRT_TWO = "Math.SQRT2"
ABS = "Math.abs"
CEIL = "Math.ceil"
COS = "Math.cos"
SIN = "Math.sin"
TAN = "Math.tan"
EXP = "Math.exp"
FLOOR = "Math.floor"
POW = "Math.pow"
SQRT = "Math.sqrt"
RANDOM = "Math.random()"
INTEGER = 0 | [1-9][0-9]*
DECIMAL = {ENTERO} \. [0-9]+
ID = [a-zA-Z_][a-zA-Z0-9_]*
SYM = [#$~%½&¬!\|@·<>\?ºª]+
COMMENT = "//"[^\r\n]* | "/*" [^*]* ("*"[^/][^*]*)* "*/"

%%

<YYINITIAL>{


}

[^]
{
    System.out.println("Simbolo Ilegal: "+yytext()+", Linea: "+(yyline+1)+", Columna: "+(yycolumn+1));
}