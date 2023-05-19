package com.mio.typeSecure.compiler.lexer;

/*
compilar archivo.jflex
java -jar jflex-full-1.9.0.jar /home/mio/Escritorio/2023/proyecto-final-compi1/src/main/jflex/tsLexer.jflex
*/

import com.mio.typeSecure.compiler.Token;
import static com.mio.typeSecure.compiler.parser.TSParserSym.*;

import com.mio.typeSecure.compiler.parser.TSParserSym;
import java_cup.runtime.Symbol;



%%

%public
%class TSLexer
%unicode
%line
%column
%type java_cup.runtime.Symbol
%cup

%{

    private Symbol symbolWithValue(int type, Object value){
        System.out.println("Encontre: "+value.toString()+" "+TSParserSym.terminalNames[type]);
        return new Symbol(type, new Token(type, value.toString(), yyline+1, yycolumn+1 ));
    }

    private Symbol symbolWithoutValue(int type){
        System.out.println("Encontre: "+TSParserSym.terminalNames[type]);
        return new Symbol(type, new Token(type, null, yyline+1, yycolumn+1 ));
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

END_LINE = \r|\n|\r\n
WHITE_SPACE = {END_LINE}|[ \t\f]
PLUS = "+"
INCREMENT = "++"
MINUS = "-"
DECREMENT = "--"
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
SYMBOL_TABLE = "getSymbolTable"
PRINT_AST = "printAst"
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
RANDOM = "Math.random"
INTEGER = 0 | [1-9][0-9]*
NUMBER_VALUE = {INTEGER}|{INTEGER} \. [0-9]+
BIGINT_VALUE = {INTEGER} "n"
STRING_VALUE = (\"[^\"]*\") | (\'[^\']*\')
ID = [a-zA-Z_][a-zA-Z0-9_]*
SYM = [#$~%½¬@·\?ºª\[\]]+
COMMENT = "//"[^\r\n]* | "/*" [^*]* ("*"[^/][^*]*)* "*/"

%%

<YYINITIAL>{

    {WHITE_SPACE}       {;}
    {COMMENT}           {;}
    {PRINT_AST}
    {
        return symbolWithoutValue(PRINT_AST);
    }
    {SYMBOL_TABLE}
    {
        return symbolWithoutValue(SYMBOL_TABLE);
    }
    {INCREMENT}
    {
        return symbolWithoutValue(INCREMENT);
    }
    {DECREMENT}
    {
        return symbolWithoutValue(DECREMENT);
    }
    {PLUS}
    {
        return symbolWithoutValue(PLUS);
    }
    {MINUS}
    {
        return symbolWithoutValue(MINUS);
    }
    {TIMES}
    {
        return symbolWithoutValue(TIMES);

    }
    {DIVIDE}
    {
        return symbolWithoutValue(DIVIDE);
    }
    {MOD}
    {
        return symbolWithoutValue(MOD);
    }
    {LPAREN}
    {
        return symbolWithoutValue(LPAREN);

    }
    {RPAREN}
    {
        return symbolWithoutValue(RPAREN);

    }
    {COLON}
    {
        return symbolWithoutValue(COLON);

    }
    {SEMICOLON}
    {
        return symbolWithoutValue(SEMICOLON);

    }
    {EQUALS}
    {
        return symbolWithoutValue(EQUALS);

    }

    {NOT_EQUALS}
    {
        return symbolWithoutValue(NOT_EQUALS);

    }

    {NOT}
    {
        return symbolWithoutValue(NOT);

    }
    {GREATER_EQ}
    {
        return symbolWithoutValue(GREATER_EQ);

    }

    {LESS_EQ}
    {
        return symbolWithoutValue(LESS_EQ);

    }
    {LESS}
    {
        return symbolWithoutValue(LESS);

    }
    {GREATER}
    {
        return symbolWithoutValue(GREATER);

    }
    {ASSIGN}
    {
        return symbolWithoutValue(ASSIGN);

    }
    {AND}
    {
        return symbolWithoutValue(AND);

    }
    {OR}
    {
        return symbolWithoutValue(OR);

    }
    {COMMA}
    {
        return symbolWithoutValue(COMMA);

    }

    {DOT}
    {
        return symbolWithoutValue(DOT);

    }
    {LBRACE}
    {
        return symbolWithoutValue(LBRACE);

    }
    {RBRACE}
    {
        return symbolWithoutValue(RBRACE);

    }
    {TRUE}
    {
        return symbolWithoutValue(TRUE);

    }

    {FALSE}
    {
        return symbolWithoutValue(FALSE);

    }
    {NUMBER}
    {
        return symbolWithoutValue(NUMBER);

    }
    {BIGINT}
    {
        return symbolWithoutValue(BIGINT);

    }
    {STRING}
    {
        return symbolWithoutValue(STRING);

    }

    {BOOLEAN}
    {
        return symbolWithoutValue(BOOLEAN);

    }

    {VOID}
    {
        return symbolWithoutValue(VOID);
    }
    {UNDEFINED}
    {
        return symbolWithoutValue(UNDEFINED);

    }

    {CONST}
    {
        return symbolWithoutValue(CONST);

    }
    {LET}
    {
        return symbolWithoutValue(LET);

    }
    {NUMBER_FUN}
    {
        return symbolWithoutValue(NUMBER_FUN);

    }
    {BIGINT_FUN}
    {
        return symbolWithoutValue(BIGINT_FUN);

    }
    {STRING_FUN}
    {
        return symbolWithoutValue(STRING_FUN);

    }
    {BOOLEAN_FUN}
    {
        return symbolWithoutValue(BOOLEAN_FUN);

    }
    {LENGTH}
    {
        return symbolWithoutValue(LENGTH);

    }

    {CHAR_AT}
    {
        return symbolWithoutValue(CHAR_AT);

    }
    {LOWER}
    {
        return symbolWithoutValue(LOWER);

    }
    {UPPER}
    {
        return symbolWithoutValue(UPPER);

    }

    {CONCAT}
    {
        return symbolWithoutValue(CONCAT);

    }

    {CONSOLE_LOG}
    {
        return symbolWithoutValue(CONSOLE_LOG);

    }

    {IF}
    {
        return symbolWithoutValue(IF);

    }

    {ELSE}
    {
        return symbolWithoutValue(ELSE);

    }

    {FOR}
    {
        return symbolWithoutValue(FOR);

    }

    {WHILE}
    {
        return symbolWithoutValue(WHILE);

    }
    {DO}
    {
        return symbolWithoutValue(DO);

    }

    {BREAK}
    {
        return symbolWithoutValue(BREAK);

    }
    {CONTINUE}
    {
        return symbolWithoutValue(CONTINUE);

    }

    {FUNCTION}
    {
        return symbolWithoutValue(FUNCTION);

    }

    {RETURN}
    {
        return symbolWithoutValue(RETURN);

    }

    {E}
    {
        return symbolWithoutValue(E);

    }

    {PI}
    {
        return symbolWithoutValue(PI);

    }

    {SQRT_TWO}
    {
        return symbolWithoutValue(SQRT_TWO);

    }
    {ABS}
    {
        return symbolWithoutValue(ABS);

    }

    {CEIL}
    {
        return symbolWithoutValue(CEIL);

    }

    {COS}
    {
        return symbolWithoutValue(COS);


    }

    {SIN}
    {
        return symbolWithoutValue(SIN);

    }

    {TAN}
    {
        return symbolWithoutValue(TAN);

    }
    {EXP}
    {
        return symbolWithoutValue(EXP);

    }

    {FLOOR}
    {
        return symbolWithoutValue(FLOOR);

    }

    {POW}
    {
        return symbolWithoutValue(POW);

    }

    {SQRT}
    {
        return symbolWithoutValue(SQRT);

    }

    {RANDOM}
    {
        return symbolWithoutValue(RANDOM);

    }

    {ID}
    {
        return symbolWithValue(ID, yytext());

    }

    {NUMBER_VALUE}
    {
        return symbolWithValue(NUMBER_VALUE, yytext());

    }
    {BIGINT_VALUE}
    {
        return symbolWithValue(BIGINT_VALUE, yytext());

    }
    {STRING_VALUE}
    {
        return symbolWithValue(STRING_VALUE, yytext().substring(1, yytext().length()-1));

    }

    {SYM}
    {
        return symbolWithValue(SYM, yytext());

    }

}

[^]
{
    System.out.println("Simbolo Ilegal: "+yytext()+", Linea: "+(yyline+1)+", Columna: "+(yycolumn+1));
}