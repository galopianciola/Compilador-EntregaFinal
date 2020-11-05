%{
package Parser;
import main.*;
import java.util.ArrayList;
import CodigoInt.*;
import javafx.util.Pair;
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
						String nuevoLexema = lexema + "@" + ambito;
						if(!Main.tSimbolos.existeLexema(nuevoLexema)){
							Main.tSimbolos.reemplazarLexema(lexema, nuevoLexema);
							DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
							dt.setUso("variable");
							dt.setTipo(tipoVar);
							dt.setDeclarada(true);
							Main.tSimbolos.setDatosTabla(nuevoLexema, dt);
						} else {
							System.out.println("La variable " + lexema + " ya fue declarada en este ambito");
							Main.tSimbolos.eliminarSimbolo(lexema);
							}
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

procedimiento : declaracion_proc '{'bloque_sentencias'}'{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaró un procedimiento");
							if($1.sval != null){ // se declaro todo bien
								ambito = ambito.substring(0,ambito.lastIndexOf("@"+$1.sval));
								Terceto t = new Terceto(FinProc, $1.sval, null);}
							}
             | declaracion_proc    bloque_sentencias'}' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
             | declaracion_proc '{'                 '}' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
             | declaracion_proc '{'bloque_sentencias    {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
             ;

declaracion_proc: PROC IDE'('lista_de_parametros')'NI'='CTE_UINT {String nuevoLexema = $2.sval + "@" + ambito;
				if(!Main.tSimbolos.existeLexema(nuevoLexema)){
					Main.tSimbolos.reemplazarLexema($2.sval, nuevoLexema);
					DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
					dt.setUso("nombreProcedimiento");
					dt.setLlamadosMax($8.sval);
					Main.tSimbolos.setDatosTabla(nuevoLexema, dt);
					lista_parametros = (ArrayList<String>)$4.obj;
					if(!lista_parametros.isEmpty()){
						int posicion = 1;
						for(String parametro : lista_parametros){
							Main.tSimbolos.reemplazarLexema(parametro, parametro +"@"+$2.sval);
							Main.tsimbolos.getDatosTabla(parametro +"@"+$2.sval).setOrden(posicion);
							posicion++;
						}
						ambito = ambito + "@"+ $2.sval;
						Tercetos t = new Terceto(PROC, nuevoLexema, null);
						adminTerceto.agregarTerceto(t);
						adminTerceto.agregarProcedimiento(nuevoLexema);
						$$ = new ParserVal(nuevoLexema); // para corroborar q el proc se declaro bien (no se si va)
					}
					else
						$$ = new ParserVal(null); // Hay 2 parametros con el mismo ide
				} else {
					System.out.print("El procedimiento "+ $2.sval + " ya fue declarado en este ambito");
					$$ = new ParserVal(null);
					}
				}
		| error_declaracion_proc
		;

error_declaracion_proc: PROC    '('lista_de_parametros')'NI'='CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
                      | PROC IDE   lista_de_parametros')'NI'='CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
                      | PROC IDE'('                   ')'NI'='CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
                      | PROC IDE'('lista_de_parametros   NI'='CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
                      | PROC IDE'('lista_de_parametros')'  '='CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
                      | PROC IDE'('lista_de_parametros')'NI   CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
                      | PROC IDE'('lista_de_parametros')'NI'=' error   {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
               	      ;

/*error_proc: PROC    '('lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
	  | PROC IDE   lista_de_parametros')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
	  | PROC IDE'('                   ')'NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
	  | PROC IDE'('lista_de_parametros   NI'='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
	  | PROC IDE'('lista_de_parametros')'  '='CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
	  | PROC IDE'('lista_de_parametros')'NI   CTE_UINT'{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
	  | PROC IDE'('lista_de_parametros')'NI'='        '{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
	  | declaracion_proc    bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
	  | declaracion_proc '{'                 '}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
	  | declaracion_proc '{'bloque_sentencias   {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
	  ;*/

lista_de_parametros : param {lista_parametros.clear();
			     lista_parametros.add($1.sval);
			     $$ = new ParserVal(lista_parametros);}
		    | param ',' param  {lista_parametros.clear();
		    			if(!$1.sval.equals($3.sval)){
						lista_parametros.add($1.sval);
						lista_parametros.add($3.sval);
					} else
						System.out.println("No puede haber dos parametros con el mismo IDE");
					$$ = new ParserVal(lista_parametros);}
		    | param ',' param ',' param {lista_parametros.clear();
		    				 if(!$1.sval.equals($3.sval) && !$1.sval.equals($5.sval) && !$3.sval.equals($5.sval)){
							lista_parametros.add($1.sval);
							lista_parametros.add($3.sval);
							lista_parametros.add($5.sval);
						 } else {
							System.out.println("No puede haber dos parametros con el mismo IDE");}
		    				 $$ = new ParserVal(lista_parametros);}
		    | error_lista_de_parametros
	            ;

error_lista_de_parametros : param ',' param ',' param ',' lista_de_parametros {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
			  | param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param ',' param param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param ',' param {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  ;

param : tipo IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);
		  DatosTabla dt = Main.tSimbolos.getDatosTabla($2.sval);
		  dt.setUso("nombreParametro");
		  dt.setTipo($1.sval);
		  Main.tSimbolos.setDatosTabla($2.sval, dt);
		  $$ = new ParserVal($2.sval);} //copia valor

      | REF tipo IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);
      		  DatosTabla dt = Main.tSimbolos.getDatosTabla($3.sval);
                  dt.setUso("nombreParametro");
                  dt.setTipo($2.sval);
                  dt.setParametroRef(true);
                  Main.tSimbolos.setDatosTabla($3.sval, dt);
                  $$ = new ParserVal($3.sval);} //referencia
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

control : FOR'('asignacion_for';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia FOR");
							if($3.sval != null){
								Terceto t = new Terceto($7.sval,$3.sval,$8.sval);
								adminTerceto.agregarTerceto(t);
								t = new Terceto("BI", null, null);
								adminTerceto.agregarTerceto(t);
								adminTerceto.desapilar(); //para completar BF
								adminTerceto.desapilarFor();
							}}
	| error_for
	;

condicion_for: condicion {if($1.sval != null){
				Terceto t = new Terceto("BF", $1.sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          }

asignacion_for: IDE '=' CTE_UINT { System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + $1.sval);
                                  String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
                                  if(ambitoVariable != null) {
                            		String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
                                        if(tipoIde.equals("UINT")){
                                		Terceto t = new Terceto("=", ambitoVariable, $3.sval);
                                		adminTerceto.agregarTerceto(t);
                                		adminTerceto.apilar(t.getNumero()+1);
                                		$$ = new ParserVal(ambitoVariable);
                                	} else
                                		System.out.println("Los tipos son incompatibles");
                                  } else {
                                	System.out.println("La variable " + $1.sval +" no fue declarada");
                                	$$ = new ParserVal(null);}
                              	  }
             | error_asignacion_for {$$ = new ParserVal(null);}
             ;

error_asignacion_for:     '=' CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador");}
		    | IDE     CTE_UINT {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
		    | IDE '=' error    {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
		    ;

error_for:   FOR    asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
	   | FOR'(' asignacion_for    condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
	   | FOR'(' asignacion_for ';'             ';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
	   | FOR'(' asignacion_for ';'condicion_for   inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
	   | FOR'(' asignacion_for ';'condicion_for';'         CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr         ')''{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT   '{'bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')'   bloque_sentencias'}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'                 '}'{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias   {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
	   ;

condicion :  expresion comparador expresion {Operando op1 = (Operando)$1.obj;
                                             Operando op2 = (Operando)$3.obj;
                                             if(op1 != null && op2 !=null){
						if(op1.getTipo().equals(op2.getTipo())){
							Terceto t = new Terceto($2.sval, op1.getValor(), op2.getValor());
							adminTerceto.agregarTerceto(t);
							$$ = new ParserVal("["+t.getNumero()+"]");
						}
						else
							System.out.println("Tipos incompatibles");
                                             }
                                             else
                                              	$$ = new ParserVal(null);}

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
                                } else
                                	$$ = new ParserVal(null);}
	  | expresion '-' termino { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una resta");
	  			Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(),"["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
                                } else
                                        $$ = new ParserVal(null);}
	  | DOUBLE '(' expresion ')'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)$3.obj;
	  			if(op != null)
	  				if(op.getTipo() == "UINT")
	  					$$ = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  				else{
	  					System.out.println("Error: no se permite convertir un double");
	  					$$ = new ParserVal(null);}
	  			else
	  				$$ = new ParserVal(null);
	  			}
         ;

termino : termino '*' factor { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una multiplicacion");
				Operando op1 = (Operando)$1.obj;
				Operando op2 = (Operando)$3.obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
				} else
                                 	$$ = new ParserVal(null);
                                }
	| termino '/' factor  { System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una division");
				Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if(op1 != null && op2 !=null){
					if (op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
					} else
						System.out.println("Tipos incompatibles");
                                } else
                                	$$ = new ParserVal(null);
                               }
	| factor { $$ = new ParserVal((Operando)$1.obj);}
        ;

factor 	: CTE_DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + $1.sval);
			$$ = new ParserVal(new Operando("DOUBLE", $1.sval));
			}
        | CTE_UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + $1.sval);
                     	$$ = new ParserVal(new Operando("UINT", $1.sval));
                        }
        | '-' factor {	if(chequearFactorNegado()){
        			Operando op = (Operando)$2.obj;
        			$$ = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
	| IDE { System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + $1.sval);
		String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
		if(ambitoVariable != null)
                	$$ = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo(), ambitoVariable));
                else {
                       	System.out.println("La variable " + $1.sval +" no fue declarada");
                       	$$ = new ParserVal(null);
                }}
        ;

comparador : '<' {$$ = new ParserVal("<");}
	   | '>' {$$ = new ParserVal(">");}
	   | IGUAL_IGUAL {$$ = new ParserVal("==");}
           | MAYOR_IGUAL {$$ = new ParserVal(">=");}
	   | MENOR_IGUAL {$$ = new ParserVal("<=");}
	   | DISTINTO {$$ = new ParserVal("!=");}
           ;

inc_decr : UP {$$ = new ParserVal("+");}
	 | DOWN {$$ = new ParserVal("-");}
	 ;

seleccion : IF '(' if_condicion ')' bloque_then END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
	  | IF '(' if_condicion ')' bloque_then ELSE /*{Terceto t = new Terceto("BI", null, null);
                                                                   adminTerceto.agregarTerceto(t);
                                                                   adminTerceto.desapilar();
                                                                   adminTerceto.apilar(t.getNumero());} */ bloque END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
	  | error_if
	  ;

bloque_then: bloque {Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
	    ;

if_condicion: condicion {//System.out.println(" se leyó una sentencia IF" + $1.sval);
				if($1.sval != null){
					Terceto t = new Terceto("BF", $1.sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
	     ;

error_if: IF     if_condicion ')' bloque END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
	| IF '('              ')' bloque END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
	| IF '(' if_condicion     bloque END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
	| IF '(' if_condicion ')'        END_IF {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
	| IF '(' if_condicion ')' bloque        {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
	//el de arriba si falta ELSE no se recuper bien, revisar.
	//| IF '(' if_condicion ')' '{' bloques '}'      '{' bloque_sentencias '}' END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el ELSE");}
	| IF '(' if_condicion ')'  bloque_then  ELSE        END_IF{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
	| IF '(' if_condicion ')'  bloque_then  ELSE bloque       {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
	;


salida : OUT'('CADENA')'{//System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");
			Terceto t = new Terceto("OUT", $3.sval, null);
			adminTerceto.agregarTerceto(t);}
       | error_salida
       ;

error_salida : OUT CADENA ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
	     | OUT '(' CADENA {System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
	     | OUT CADENA {System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
	     | OUT '('error')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
	     | OUT '(' ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
	     ;

asignacion : IDE '=' expresion {System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignación al identificador -> " + $1.sval);
				String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
				if(ambitoVariable != null){
					String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
					Operando op = (Operando)$3.obj;
					if(op != null)
						if(tipoIde.equals(op.getTipo())){
							Terceto t = new Terceto("=", ambitoVariable, op.getValor());
							adminTerceto.agregarTerceto(t);
							$$ = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
						} else
							System.out.println("Los tipos son incompatibles");
				} else {
					System.out.println("La variable " + $1.sval +" no fue declarada");
					// ver si devolver null}
				}}
	   | error_asignacion
	   ;

error_asignacion : IDE expresion {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
		 | '=' expresion {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
		 | IDE '=' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
		 ;


invocacion : IDE '(' parametros ')'{//System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + $1.sval );
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)$3.obj;
			  	   if(!lista_param_invocacion.isEmpty()){ // Hubo un error mas abajo
			  	    	String ambitoProc = Main.tSimbolos.verificarAmbito($1.sval, ambito);
			  	    	if(ambitoProc != null && this.verificarParametros(lista_param_invocacion, $1.sval) && main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosActuales() < main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosMax()){
			  	    		for(Pair p : lista_param_invocacion){
			  	    			Terceto t = new Terceto("=" ,p.getKey()+"@"+$1.sval, p.getValue());
			  	    			adminTerceto.agregarTerceto(t);
			  	    		}
			  	    		Terceto t = new Terceto("INV", ambitoProc, null); //ver como guardar linea inicial de procedimiento.
			  	    		Main.tSimbolos.getDatosTabla(ambitoProc).incrementarLlamados();
			  	    	} else
			  	    		System.out.println("El procedimiento "+$1.sval+" esta fuera de alcance");
			  	   }}
	   | error_invocacion
	   ;

error_invocacion: '(' parametros ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
		| IDE parametros ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
		| IDE '(' ')' {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
		| IDE'('parametros {System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
		;

parametros : IDE ':' IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $1.sval +" y " +$3.sval);
			  lista_param_invocacion.clear();
			  String ambitoVariable = Main.tSimbolos.verificarAmbito($3.sval, ambito);
			  if(ambitoVariable != null){
			  	lista_param_invocacion.add(new Pair<String,String>($1.sval, ambitoVariable));
			  	$$ = new ParserVal(lista_param_invocacion);} // esto no se si como seria pq hay 2 listas :'(
			  else
			  	System.out.println("La variable "+$3.sval+ "no se encuentra en el ambito");
			  }
	   | parametros ',' IDE ':' IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $3.sval +" y " +$5.sval);
                               	lista_param_invocacion = (ArrayList<Pair<String, String>>)$1.obj;
                               	if(!lista_param_invocacion.isEmpty()){
                               		String ambitoVariable = Main.tSimbolos.verificarAmbito($5.sval, ambito);
                                        if(ambitoVariable != null){
                                        	lista_param_invocacion.add(new Pair<String,String>($3.sval, ambitoVariable));
                                    		$$ = new ParserVal(lista_param_invocacion);
                                     	} else
                                        	System.out.println("La variable "+$5.sval+ " no se encuentra en el ambito");
                                }}
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
private ArrayList<String> lista_parametros;
private ArrayList<Pair<String,String>> lista_param_invocacion;
private AdmTercetos adminTerceto;
private String ambito;

public Parser(Lexico lexico, AdmTercetos adminTerceto)
{
  this.lexico = lexico;
  this.lista_variables = new ArrayList<String>();
  this.lista_parametros = new ArrayList<String>();
  this.lista_param_invocacion = new ArrayList<>();
  this.adminTerceto = adminTerceto;
  this.ambito = "main";
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
    //System.out.println("Parser: " + s);  Comentando esto no tira lo de Parser: sintax error
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

public boolean verificarParametros(ArrayList<Pair<String,String>> lista, String proc){
	int orden = 1;
	for(Pair p : lista){
		String parametroFormal = p.getKey() + "@" + proc;
		String parametroReal = p.getValue();
		if(!Main.tSimbolos.existeLexema(parametroFormal)){ //el usuario lo escribio mal en la invocacion
			return false;
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getOrden() != orden)
			return false;
		if((Main.tSimbolos.getDatosTabla(parametroFormal).getTipo() != Main.tSimbolos.getDatosTabla(parametroReal).getTipo())
			return false;
		orden++;
	}
	return true;
}
