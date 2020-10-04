%{
package Parser;
import main.*;
%}

%token IDE CTE_UINT MAYOR_IGUAL MENOR_IGUAL IGUAL_IGUAL DISTINTO CTE_DOUBLE CADENA IF THEN ELSE END_IF OUT UINT DOUBLE
NI REF FOR UP DOWN PROC FUNC RETURN
%start programa

%%

programa: bloque
        ;

bloque : sentencia
       | '{'bloque_sentencias'}'
       | error_bloque
       ;


error_bloque : error bloque_sentencias '}' {System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '{'");}
      	     | '{' bloque_sentencias error {System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '}'");}
             ;

bloque_sentencias  :  sentencia
                   |  bloque_sentencias sentencia
                   ;

sentencia  : declaracion ';'{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una sentencia declarativa");}
           | ejecucion ';'{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una sentencia de ejecución");}
           | error_sentencia
           ;

error_sentencia : declaracion error {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
           	| ejecucion error {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
           	;

declaracion  : tipo lista_de_variables{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion");}
    	     | procedimiento
             ;

lista_de_variables : IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] lei un ID");}
      		   | lista_de_variables ',' IDE
                   ;

procedimiento : PROC IDE'('lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaro una PROC");}
              ;

lista_de_parametros : param
		    | param ',' param
		    | param ',' param ',' param
	            ;

param : tipo IDE
      | REF tipo IDE
      ;

tipo : UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] lei un tipo UINT");}
     | DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] lei un tipo DOUBLE");}
     ;

ejecucion : control
	  | seleccion
	  | salida
	  | asignacion
	  | invocacion
	  ;

control : FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{ System.out.println("[Parser | Linea " + Lexico.linea + "] lei un FOR");}
	;

condicion :  expresion comparador expresion
          ;

expresion : expresion '+' termino { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una suma");}
	  | expresion '-' termino { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una resta");}
	  | termino
	  | DOUBLE '(' expresion ')'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una conversion");}
          ;

termino : termino '*' factor { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una multiplicacion");}
	| termino '/' factor  { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una division");}
	| factor
        ;

factor 	: cte
	| factor_negado
	| IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un identificador");}
        ;

cte : CTE_DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una cte double");}
    | CTE_UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una cte uint");}
    ;

factor_negado : '-' CTE_DOUBLE
              ;

comparador : '<'
	   | '>'
	   | IGUAL_IGUAL
           | MAYOR_IGUAL
	   | MENOR_IGUAL
	   | DISTINTO
           ;

inc_decr : UP
	 | DOWN
	 ;

seleccion : IF '(' condicion ')' '{' bloque_sentencias '}' END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un IF");}
	  | IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' bloque_sentencias '}' END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un IF con ELSE");}
	  ;


salida : OUT'('CADENA')'{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó un OUT");}
       ;

asignacion : IDE '=' expresion {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignacion");}
           ;

invocacion : IDE '(' parametros ')' {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion a una funcion");}
	   ;

parametros : IDE ':' IDE
	   | parametros ',' IDE ':' IDE
	   ;

%%

private Lexico lexico;
public Parser(Lexico lexico)
{
  this.lexico= lexico;
}

public int yylex(){
   Token token = this.lexico.getToken();
   if(token != null ){
   	int val =token.getId();
   	yyval = new ParserVal(token.getLexema());
   	return val;
   }
   return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}