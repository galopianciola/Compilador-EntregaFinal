%{
package Parser;
import main.*;
import java.util.ArrayList;
import CodigoInt.*;
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


error_bloque : bloque_sentencias '}' {System.out.println("Error sináctico: Linea " + Lexico.linea + " se detectó un bloque de sentencias mal declarado, falta '{'");}
      	     | '{' bloque_sentencias  {System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '}'");}
             ;

bloque_sentencias  :  sentencia
                   |  bloque_sentencias sentencia
                   ;

sentencia  : declaracion
           | ejecucion
           ;

declaracion : tipo lista_de_variables';'{//System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion de variables");
					String tipoVar = $1.sval;
					lista_variables = (ArrayList<String>)$2.obj; //controlar si ya está en la tabla
					for(String lexema : lista_variables){
						Main.tSimbolos.setDatosTabla(lexema,"variable",tipoVar,true);
					}
					lista_variables.clear();
					}

    	     | procedimiento';'
    	     | error_declaracion
             ;

error_declaracion : tipo lista_de_variables error {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
           	  | procedimiento error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
           	  ;

lista_de_variables : lista_de_variables ',' IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $3.sval);}
      		   				 lista_variables = (ArrayList<String>) $1.obj;
                                                 lista_variables.add($3.sval);
                                                 $$ = new ParserVal(lista_variables);
                                                 }
		   | IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $1.sval);}
                          	lista_variables.add($1.sval);
                                $$ = new ParserVal(lista_variables);
                                }
      		   | error_lista_de_variables
                   ;

error_lista_de_variables: lista_de_variables IDE {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ',' entre los identificadores");}

procedimiento : PROC IDE'('lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaró un procedimiento");}
              | error_proc
              ;

error_proc: PROC    '('lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
	  | PROC IDE   lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
	  | PROC IDE'('                   ')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
	  | PROC IDE'('lista_de_parametros   NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
	  | PROC IDE'('lista_de_parametros')'  '='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
	  | PROC IDE'('lista_de_parametros')'NI   CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
	  | PROC IDE'('lista_de_parametros')'NI'='        '{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
	  | PROC IDE'('lista_de_parametros')'NI'='CTE_UINT   bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
	  | PROC IDE'('lista_de_parametros')'NI'='CTE_UINT'{'                 '}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
	  | PROC IDE'('lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias   {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}


lista_de_parametros : param
		    | param ',' param
		    | param ',' param ',' param
		    | error_lista_de_parametros
	            ;

error_lista_de_parametros : param ',' param ',' param ',' lista_de_parametros {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
			  | param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param ',' param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param ',' param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  ;

param : tipo IDE {System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);}
      | REF tipo IDE {System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);}
      ;

tipo : UINT {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo UINT");}
		$$ = new ParserVal ("UINT");}
     | DOUBLE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo DOUBLE");
     		$$ = new ParserVal ("DOUBLE");}
     ;

ejecucion : control';'
	  | seleccion';'
	  | salida ';'
	  | asignacion ';'
	  | invocacion';'
	  | error_ejecucion
	  ;

error_ejecucion  : control error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
               	 | seleccion error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
                 | salida error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
           	 | asignacion error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
             	 | invocacion error{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
               	 ;

control : FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia FOR");}
	| error_for
	;

error_for: FOR   IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
	 | FOR'('   '='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador ");}
	 | FOR'('IDE   CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
	 | FOR'('IDE'='        ';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
	 | FOR'('IDE'='CTE_UINT   condicion';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
	 | FOR'('IDE'='CTE_UINT';'         ';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
	 | FOR'('IDE'='CTE_UINT';'condicion   inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'         CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'inc_decr         ')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT   '{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')'   bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'                 '}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
	 | FOR'('IDE'='CTE_UINT';'condicion';'inc_decr CTE_UINT')''{'bloque_sentencias   {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}

condicion :  expresion comparador expresion
          ;

expresion : termino { $$ = new ParserVal((Operando)$1.obj);}
	  | expresion '+' termino {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una suma");
				Operando op1 = (Operando)$1.obj;
				Operando op2 = (Operando)$3.obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("+", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
                                }}
	  | expresion '-' termino { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una resta");
	  			Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if(op1.getTipo().equals(op2.getTipo())){
                                	Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
                                	adminTerceto.agregarTerceto(t);
                                        $$ = new ParserVal(new Operando(op1.getTipo(),"["+t.getNumero()+"]"));
                                        }
                                else
                                  	System.out.println("Tipos incompatibles");
                                }
	  | DOUBLE '(' expresion ')'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)$3.obj;
	  			$$ = new ParserVal(new Operando("DOUBLE",op.getValor()));}
          ;

termino : termino '*' factor { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una multiplicacion");
				Operando op1 = (Operando)$1.obj;
				Operando op2 = (Operando)$3.obj;
				if(op1.getTipo().equals(op2.getTipo())){
					Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
					adminTerceto.agregarTerceto(t);
					$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
					}
				else
					System.out.println("Tipos incompatibles");
				}
	| termino '/' factor  { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una division");
				Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if (op1.getTipo().equals(op2.getTipo())){
                                	Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
                                	adminTerceto.agregarTerceto(t);
                                        $$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
                                } else {
                                	System.out.println("Tipos incompatibles");
                                }}
	| factor { $$ = new ParserVal((Operando)$1.obj);}
        ;

factor 	: CTE_DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + $1.sval);
			if(Main.tSimbolos.getDatosTabla($1.sval).isDeclarada())
				$$ = new ParserVal(new Operando("DOUBLE", $1.sval));
			else
				System.out.print("La variable " + $1.sval +" no fue declarada");}
        | CTE_UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + $1.sval);
        		if(Main.tSimbolos.getDatosTabla($1.sval).isDeclarada())
                        	$$ = new ParserVal(new Operando("UINT", $1.sval));
                        else
                        	System.out.print("La variable " + $1.sval +" no fue declarada");}
        | '-' factor {	if(chequearFactorNegado()){
        			Operando op = (Operando)$2.obj;
        			$$ = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
	| IDE { System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + $1.sval);
		if(Main.tSimbolos.getDatosTabla($1.sval).isDeclarada())
                	$$ = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla($1.sval).getTipo(), $1.sval));
                else {
                       	System.out.print("La variable " + $1.sval +" no fue declarada");
                       	$$ = new ParserVal(null);
                }}
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

seleccion : IF '(' condicion ')' '{' bloque_sentencias '}' END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");}
	  | IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' bloque_sentencias '}' END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");}
	  | error_if
	  ;

error_if: IF     condicion ')' '{' bloque_sentencias '}' END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
	| IF '('           ')' '{' bloque_sentencias '}' END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
	| IF '(' condicion     '{' bloque_sentencias '}' END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
	| IF '(' condicion ')'     bloque_sentencias '}' END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '{'");}
	| IF '(' condicion ')' '{'                   '}' END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
	| IF '(' condicion ')' '{' bloque_sentencias     END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '}'");}
	| IF '(' condicion ')' '{' bloque_sentencias '}'        {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
	| IF '(' condicion ')' '{' bloque_sentencias '}'      '{'bloque_sentencias '}' END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el ELSE");}
	| IF '(' condicion ')' '{' bloque_sentencias '}' ELSE    bloque_sentencias '}' END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '{'");}
	| IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{'                  '}' END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
	| IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{'bloque_sentencias     END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '}'");}
	| IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{'bloque_sentencias '}'       {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
	;



salida : OUT'('CADENA')'{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
       | error_salida
       ;

error_salida : OUT CADENA ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
	     | OUT '(' CADENA {System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
	     | OUT CADENA {System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
	     | OUT '('error')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
	     | OUT '(' ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
	     ;

asignacion : IDE '=' expresion {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignación al identificador -> " + $1.sval);
				String tipoIde = Main.tSimbolos.getDatosTabla($1.sval).getTipo();
				Operando op = (Operando)$3.obj;
				if(op != null){
					if(tipoIde.equals(op.getTipo())){
						Terceto t = new Terceto("=", $1.sval, op.getValor());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
					} else
						System.out.println("Los tipos son incompatibles");
				}}
	   | error_asignacion
	   ;

error_asignacion : IDE expresion {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
		 | '=' expresion {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
		 | IDE '=' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
		 ;


invocacion : IDE '(' parametros ')' {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + $1.sval );}
	   | error_invocacion
	   ;

error_invocacion: '(' parametros ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
		| IDE parametros ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
		| IDE '(' ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
		| IDE'('parametros {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
		;

parametros : IDE ':' IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $1.sval +" y " +$3.sval);}
	   | parametros ',' IDE ':' IDE  {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $3.sval +" y " +$5.sval);}
	   | error_parametros
	   ;

error_parametros : ':' IDE {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
		 | IDE IDE {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
		 | parametros IDE ':' IDE {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
		 | IDE ':' error {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
		 ;
%%

private Lexico lexico;
private ArrayList<String> lista_variables;
private AdmTercetos adminTerceto;

public Parser(Lexico lexico, AdmTercetos adminTerceto)
{
  this.lexico = lexico;
  this.lista_variables = new ArrayList<String>();
  this.adminTerceto = adminTerceto;
}

public int yylex(){
   Token token = this.lexico.getToken();
   if(token != null ){
   	int val = token.getId();
   	yylval = new ParserVal(token.getLexema());
   	return val;
   }
   return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}

public boolean chequearFactorNegado(){
	String lexema = yylval.sval;
	int id = Main.tSimbolos.getId(lexema);
	if(id == Lexico.CTE_UINT){
		System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una constante UINT fuera de rango");
		Main.tSimbolos.eliminarSimbolo(lexema);
	}
	else if (id == Lexico.CTE_DOUBLE) {
		double valor = -1*Double.parseDouble(lexema.replace('d','e'));
		if(( valor > 2.2250738585272014e-308 && valor < 1.7976931348623157e+308) || (valor > -1.7976931348623157e+308 && valor < -2.2250738585072014e-308) || (valor == 0.0))
                	Main.tSimbolos.modificarSimbolo(lexema, String.valueOf(valor));
                	return true;
                	}
                else {
                	System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una constante DOUBLE fuera de rango");
	               	Main.tSimbolos.eliminarSimbolo(lexema);
	 	}
	return false;
}
