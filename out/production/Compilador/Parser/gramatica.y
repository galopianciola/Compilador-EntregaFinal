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


error_bloque : bloque_sentencias '}' {Main.listaErrores.add("Error sin�ctico: Linea " + Lexico.linea + " se detect� un bloque de sentencias mal declarado, falta '{'");}
      	     | '{' bloque_sentencias {Main.listaErrores.add("Error sint�ctico: Linea "+ Lexico.linea+ " se detect� un bloque de sentencias mal declarado, falta '}'");}
             ;

bloque_sentencias  :  sentencia
                   |  bloque_sentencias sentencia
                   ;

sentencia  : declaracion
           | ejecucion
           ;

declaracion : tipo lista_de_variables';'{//System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion de variables");
					String tipoVar = $1.sval;
					lista_variables = (ArrayList<String>)$2.obj; //controlar si ya est� en la tabla
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
							Main.listaErrores.add("Error semántico: Linea " + Lexico.linea+ " la variable " + lexema + " ya fue declarada en este ambito");
							Main.tSimbolos.eliminarSimbolo(lexema);
							}
					}
					lista_variables.clear();
					}

    	     | procedimiento';'
    	     | error_declaracion
             ;

error_declaracion : tipo lista_de_variables error {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
           	  | procedimiento error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
           	  ;

lista_de_variables : lista_de_variables ',' IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $3.sval);
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

error_lista_de_variables: lista_de_variables IDE {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ',' entre los identificadores");}

procedimiento : declaracion_proc '{'bloque_sentencias'}'{System.out.println("[Parser | Linea " + Lexico.linea + "]se declar� un procedimiento");
							if($1.sval != null){ // se declaro todo bien
								ambito = ambito.substring(0,ambito.lastIndexOf("@"));
								Terceto t = new Terceto("FinProc", $1.sval, null);
								adminTerceto.agregarTerceto(t);
								}
							}
	     | error_proc
	     ;


error_proc:    declaracion_proc    bloque_sentencias'}' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
             | declaracion_proc '{'                 '}' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta el bloque de sentencias");}
             | declaracion_proc '{'bloque_sentencias    {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
             ;

declaracion_proc: PROC IDE'('lista_de_parametros')'NI'='CTE_UINT {
			lista_parametros = (ArrayList<String>)$4.obj;
			if(!lista_parametros.isEmpty()){
				String nuevoLexema = $2.sval + "@" + ambito;
				if(!Main.tSimbolos.existeLexema(nuevoLexema)){
					Main.tSimbolos.reemplazarLexema($2.sval, nuevoLexema);
					DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
					dt.setUso("nombreProcedimiento");
					dt.setLlamadosMax(Integer.parseInt($8.sval));
					dt.setCantParametros(lista_parametros.size());
					Main.tSimbolos.setDatosTabla(nuevoLexema, dt);

					int posicion = 1;
					for(String parametro : lista_parametros){
						Main.tSimbolos.reemplazarLexema(parametro, parametro +"@"+$2.sval);
						Main.tSimbolos.getDatosTabla(parametro +"@"+$2.sval).setOrden(posicion);
						posicion++;
					}
					ambito = ambito + "@"+ $2.sval;
					Terceto t = new Terceto("ComienzaProc", nuevoLexema, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.agregarProcedimiento(nuevoLexema);
					$$ = new ParserVal(nuevoLexema); // para corroborar q el proc se declaro bien (no se si va)
				} else {
					Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " el procedimiento "+ $2.sval + " ya fue declarado en este ambito");
					$$ = new ParserVal(null);
					}
			}}
		| error_declaracion_proc
		;

error_declaracion_proc: PROC    '('lista_de_parametros')'NI'='CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta el identificador");}
                      | PROC IDE   lista_de_parametros')'NI'='CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '('");}
                      | PROC IDE'('                   ')'NI'='CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta lista de parametros");}
                      | PROC IDE'('lista_de_parametros   NI'='CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta ')'");}
                      | PROC IDE'('lista_de_parametros')'  '='CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta la palabra reservada NI ");}
                      | PROC IDE'('lista_de_parametros')'NI   CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, '=' despues de NI ");}
                      | PROC IDE'('lista_de_parametros')'NI'=' error   {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta la constante UINT ");}
               	      ;

lista_de_parametros : param {lista_parametros.clear();
			     lista_parametros.add($1.sval);
			     $$ = new ParserVal(lista_parametros);}
		    | param ',' param  {lista_parametros.clear();
		    			if(!$1.sval.equals($3.sval)){
						lista_parametros.add($1.sval);
						lista_parametros.add($3.sval);
					} else
						Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " no puede haber dos parametros con el mismo IDE");
					$$ = new ParserVal(lista_parametros);}
		    | param ',' param ',' param {lista_parametros.clear();
		    				 if(!$1.sval.equals($3.sval) && !$1.sval.equals($5.sval) && !$3.sval.equals($5.sval)){
							lista_parametros.add($1.sval);
							lista_parametros.add($3.sval);
							lista_parametros.add($5.sval);
						 } else {
							Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " no puede haber dos parametros con el mismo IDE");}
		    				 $$ = new ParserVal(lista_parametros);}
		    | error_lista_de_parametros {lista_parametros.clear();
		    				 $$ = new ParserVal(lista_parametros);}
	            ;

error_lista_de_parametros : param ',' param ',' param ',' lista_de_parametros {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron m�s parametros de los permitidos (3)");}
			  | param param {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param param {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param ',' param param {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  | param param ',' param {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
			  ;

param : tipo IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el parametro -> " + $2.sval);
		  DatosTabla dt = Main.tSimbolos.getDatosTabla($2.sval);
		  dt.setUso("nombreParametro");
		  dt.setTipo($1.sval);
		  Main.tSimbolos.setDatosTabla($2.sval, dt);
		  $$ = new ParserVal($2.sval);} //copia valor

      | REF tipo IDE {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el parametro -> " + $2.sval);
      		  DatosTabla dt = Main.tSimbolos.getDatosTabla($3.sval);
                  dt.setUso("nombreParametro");
                  dt.setTipo($2.sval);
                  dt.setParametroRef(true);
                  Main.tSimbolos.setDatosTabla($3.sval, dt);
                  $$ = new ParserVal($3.sval);} //referencia
      ;

tipo : UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� un tipo UINT");
		$$ = new ParserVal ("UINT");}
     | DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� un tipo DOUBLE");
     		$$ = new ParserVal ("DOUBLE");}
     ;

ejecucion : control';'
	  | seleccion';'
	  | salida ';'
	  | asignacion ';'
	  | invocacion';'
	  | error_ejecucion
	  ;

error_ejecucion  : control error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
               	 | seleccion error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
                 | salida error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
           	 | asignacion error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
             	 | invocacion error{Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
               	 ;

control : FOR'('asignacion_for';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia FOR");
							if(($3.sval != null) && ($5.sval != null)){
								Terceto t = new Terceto($7.sval,$3.sval,$8.sval);
								t.setTipo("UINT");
								adminTerceto.agregarTerceto(t);
								t = new Terceto("BI", null, null);
								adminTerceto.agregarTerceto(t);
								adminTerceto.desapilar(); //para completar BF
								adminTerceto.desapilarFor();
								t = new Terceto("Label"+adminTerceto.cantTercetos(),null,null);
								adminTerceto.agregarTerceto(t);
							}}
	| error_for
	;

condicion_for: condicion {if($1.sval != null){
				Terceto t = new Terceto("BF", $1.sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          else
                          	$$ = new ParserVal(null);
                          }


asignacion_for: IDE '=' CTE_UINT { System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz? una asignaci?n al identificador -> " + $1.sval);
                                  String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
                                  if(ambitoVariable != null) {
                            		String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
                                        if(tipoIde.equals("UINT")){
                                		Terceto t = new Terceto("=", ambitoVariable, $3.sval);
                                		t.setTipo("UINT");
                                		adminTerceto.agregarTerceto(t);
                                		t = new Terceto("Label"+adminTerceto.cantTercetos(), null, null);
						t.setTipo("UINT");
                                		adminTerceto.agregarTerceto(t);
                                		adminTerceto.apilar(t.getNumero());
                                		$$ = new ParserVal(ambitoVariable);
                                	} else
                                		Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los tipos son incompatibles");
                                  } else {
                                	Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " la variable " + $1.sval +" no fue declarada");
                                	$$ = new ParserVal(null);}
                              	  }
             | error_asignacion_for {	$$ = new ParserVal(null);}
             ;

error_asignacion_for:     '=' CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el identificador");}
		    | IDE     CTE_UINT {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '='");}
		    | IDE '=' error    {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante UINT");}
		    ;

error_for:   FOR    asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '('");}
	   | FOR'(' asignacion_for    condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
	   | FOR'(' asignacion_for ';'             ';'inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la condici�n");}
	   | FOR'(' asignacion_for ';'condicion_for   inc_decr CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
	   | FOR'(' asignacion_for ';'condicion_for';'         CTE_UINT')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la palabra UP o DOWN");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr         ')''{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante CTE_UINT");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT   '{'bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ')'");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')'   bloque_sentencias'}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '{'");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'                 '}'{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el bloque de sentencias");}
	   | FOR'(' asignacion_for ';'condicion_for';'inc_decr CTE_UINT')''{'bloque_sentencias   {System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '}'");}
	   ;

condicion :  expresion comparador expresion {Operando op1 = (Operando)$1.obj;
                                             Operando op2 = (Operando)$3.obj;
                                             if(op1 != null && op2 !=null){
						if(op1.getTipo().equals(op2.getTipo())){
							Terceto t = new Terceto($2.sval, op1.getValor(), op2.getValor());
							t.setTipo(op1.getTipo());
							adminTerceto.agregarTerceto(t);
							$$ = new ParserVal("["+t.getNumero()+"]");
						}
						else
							Main.listaErrores.add("Error semántico: Linea " + Lexico.linea +" los tipos son incompatibles");

                                             }
                                             else
                                              	$$ = new ParserVal(null);
                                             }

expresion : termino { $$ = new ParserVal((Operando)$1.obj);}
	  | expresion '+' termino {System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una suma");
				Operando op1 = (Operando)$1.obj;
				Operando op2 = (Operando)$3.obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("+", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else {
						Main.listaErrores.add("Error semántico: Linea " + Lexico.linea +" los tipos son incompatibles");
						$$ = new ParserVal(null);
						}
                                } else
                                	$$ = new ParserVal(null);
                                	}
	  | expresion '-' termino { System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una resta");
	  			Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(),"["+t.getNumero()+"]"));
						}
					else{
						Main.listaErrores.add("Error semántico: Linea " + Lexico.linea +" los tipos son incompatibles");
						$$ = new ParserVal(null);}
                                } else
                                        $$ = new ParserVal(null);
                                	}
	  | DOUBLE '(' expresion ')'{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una conversi�n");
	  			Operando op = (Operando)$3.obj;
	  			if(op != null)
	  				if(op.getTipo() == "UINT"){
	  					$$ = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  					Terceto t = new Terceto("CONV", op.getValor(), null);
	  					adminTerceto.agregarTerceto(t);
	  					$$ = new ParserVal(new Operando("DOUBLE","["+t.getNumero()+"]"));
	  					}
	  				else{
	  					Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " no se permite convertir un double");
	  					$$ = new ParserVal(null);}
	  			else
	  				$$ = new ParserVal(null);
	  			}
         ;

termino : termino '*' factor { System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una multiplicacion");
				Operando op1 = (Operando)$1.obj;
				Operando op2 = (Operando)$3.obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else{
						Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los tipos son incompatibles");
						$$ = new ParserVal(null);}
				} else
                                 	$$ = new ParserVal(null);
                                }
	| termino '/' factor  { System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una division");
				Operando op1 = (Operando)$1.obj;
                                Operando op2 = (Operando)$3.obj;
                                if(op1 != null && op2 !=null){
					if (op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						$$ = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
					} else{
						Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los tipos son incompatibles");
						$$ = new ParserVal(null);}
                                } else
                                	$$ = new ParserVal(null);
                               }
	| factor { $$ = new ParserVal((Operando)$1.obj);}
        ;

factor 	: CTE_DOUBLE {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante double -> " + $1.sval);
			$$ = new ParserVal(new Operando("DOUBLE", $1.sval));
			}
        | CTE_UINT {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante uint -> " + $1.sval);
                     	$$ = new ParserVal(new Operando("UINT", $1.sval));
                        }
        | '-' factor {	if(chequearFactorNegado()){
        			Operando op = (Operando)$2.obj;
        			$$ = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
	| IDE { System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el identificador -> " + $1.sval);
		String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
		if(ambitoVariable != null)
                	$$ = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo(), ambitoVariable));
                else {
                       	Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " la variable " + $1.sval +" no fue declarada");
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

seleccion : IF '(' if_condicion ')' bloque END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF");
						   if($3.sval != null)
						   	{adminTerceto.desapilar();
						   	Terceto t = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
                                                        adminTerceto.agregarTerceto(t);}
                                                        }
	  | IF '(' if_condicion ')' bloque {if($3.sval != null){
	  					Terceto t = new Terceto("BI", null, null);
                                            	adminTerceto.agregarTerceto(t);
                                                adminTerceto.desapilar();
                                                Terceto t1 = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
                                                adminTerceto.agregarTerceto(t1);
                           	            	adminTerceto.apilar(t.getNumero());
                           	           	}
                                            } ELSE bloque END_IF {System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF con ELSE");
	  			                                   if($3.sval != null) {
	  			                                   adminTerceto.desapilar();
	  			                                   Terceto t = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
	  			                                   adminTerceto.agregarTerceto(t);}}
	  | error_if
	  ;

if_condicion: condicion {if($1.sval != null){
				Terceto t = new Terceto("BF", $1.sval, null);
				adminTerceto.agregarTerceto(t);
				adminTerceto.apilar(t.getNumero());
			}
			else
				$$ = new ParserVal(null);
			}
	     ;

error_if: IF     if_condicion ')' bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
	| IF '('              ')' bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
	| IF '(' if_condicion     bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
	| IF '(' if_condicion ')'        END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias");}
	| IF '(' if_condicion ')' bloque        {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el END_IF o ELSE");}
	// el de arriba si falta ELSE no se recuper bien, revisar.
//	| IF '(' if_condicion ')' bloque      bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el ELSE");}
	| IF     if_condicion ')' bloque ELSE bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
	| IF '('              ')' bloque ELSE bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
	| IF '(' if_condicion     bloque ELSE bloque END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
//	| IF '(' if_condicion ')' bloque ELSE        END_IF {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias del ELSE");}
//	| IF '(' if_condicion ')' bloque ELSE bloque        {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el END_IF");}
	;

salida : OUT'('CADENA')'{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una sentencia OUT");
			Terceto t = new Terceto("OUT", $3.sval, null);
			adminTerceto.agregarTerceto(t);}
       | error_salida
       ;

error_salida : OUT CADENA ')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta '('");}
	     | OUT '(' CADENA {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, falta ')'");}
	     | OUT CADENA {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, faltan '(' y ')'");}
	     | OUT '('error')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, entre los par�ntesis debe ir una cadena");}
	     | OUT '(' ')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta la cadena entre los parent�sis en el OUT");}
	     ;

asignacion : IDE '=' expresion {System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + $1.sval);
				String ambitoVariable = Main.tSimbolos.verificarAmbito($1.sval, ambito);
				if(ambitoVariable != null){
					String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
					Operando op = (Operando)$3.obj;
					if(op != null)
						if(tipoIde.equals(op.getTipo())){
							Terceto t = new Terceto("=", ambitoVariable, op.getValor());
							t.setTipo(op.getTipo());
							adminTerceto.agregarTerceto(t);
							$$ = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
						} else{
							Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los tipos son incompatibles");
				} else {
					Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + "La variable " + $1.sval +" no fue declarada");
					// ver si devolver null
					}
				}}
	   | error_asignacion
	   ;

error_asignacion : IDE expresion {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " falta '=' en la asignaci�n");}
		 | '=' expresion {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignaci�n");}
		 | IDE '=' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " falta una expresi�n aritm�tica del lado derecho de la asignaci�n");}
		 ;


invocacion : IDE '(' parametros ')'{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una invocacion al procedimiento -> " + $1.sval );
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)$3.obj;
			  	   if(lista_param_invocacion!= null && !lista_param_invocacion.isEmpty()){ // Hubo un error mas abajo
			  	    	String ambitoProc = Main.tSimbolos.verificarAmbito($1.sval, ambito);
			  	    	if(ambitoProc != null){
			  	    	   	if(lista_param_invocacion.size() == Main.tSimbolos.getDatosTabla(ambitoProc).getCantParametros()){
							if (verificarParametros($1.sval)){
								if(Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosActuales() < Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosMax()){
									for(Pair p : lista_param_invocacion){
										Terceto t = new Terceto("=" ,p.getKey()+"@"+$1.sval, (String)p.getValue());
										t.setTipo(Main.tSimbolos.getDatosTabla(p.getKey()+"@"+$1.sval).getTipo());
										adminTerceto.agregarTerceto(t);
									}
									Terceto t = new Terceto("INV", ambitoProc, null); //ver como guardar linea inicial de procedimiento.
									Main.tSimbolos.getDatosTabla(ambitoProc).incrementarLlamados();
									adminTerceto.agregarTerceto(t);
								} else
									Main.listaErrores.add("Error semántico: Linea " + Lexico.linea+ " supero la cantidad maxima de llamados a "+$1.sval);
							}
						}else
							Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " faltan parametros para invocar al procedimiento "+$1.sval);
			  	    	}else
			  	    		Main.listaErrores.add("Error semántico: Linea " + Lexico.linea+ " el procedimiento "+$1.sval+" esta fuera de alcance");
			  	   }}
	   | error_invocacion
	   ;

error_invocacion: '(' parametros ')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el identificador");}
		| IDE parametros ')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el '('");}
		| IDE '(' ')' {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, faltan los par�metros");}
		| IDE'('parametros {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el ')'");}
		;

parametros : IDE ':' IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los par�metros -> " + $1.sval +" y " +$3.sval);
			  if(lista_param_invocacion!=null){
				  lista_param_invocacion.clear();
				  String ambitoVariable = Main.tSimbolos.verificarAmbito($3.sval, ambito);
				  if(ambitoVariable != null){
					lista_param_invocacion.add(new Pair<String,String>($1.sval, ambitoVariable));
					$$ = new ParserVal(lista_param_invocacion);}
				  else
					Main.listaErrores.add("Error semántico: Linea " + Lexico.linea+ " la variable "+$3.sval+ "no se encuentra en el ambito");
				  }
				  }
	   | parametros ',' IDE ':' IDE {//System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los par�metros -> " + $3.sval +" y " +$5.sval);
                               	lista_param_invocacion = (ArrayList<Pair<String, String>>)$1.obj;
                               	if(lista_param_invocacion != null && !lista_param_invocacion.isEmpty()){
                               		String ambitoVariable = Main.tSimbolos.verificarAmbito($5.sval, ambito);
                                        if(ambitoVariable != null){
                                        	lista_param_invocacion.add(new Pair<String,String>($3.sval, ambitoVariable));
                                    		$$ = new ParserVal(lista_param_invocacion);
                                     	} else
                                        	Main.listaErrores.add("Error semántico: Linea " + Lexico.linea+ " la variable "+$5.sval+ " no se encuentra en el ambito");
                                }}
	   | error_parametros
	   ;

error_parametros : ':' IDE {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la izquierda");}
		 | IDE IDE {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta ':' entre los identificadores");}
		 | parametros IDE ':' IDE {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta la ',' que separa los identificadores");}
		 | IDE ':' error {Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la derecha");}
		 ;
%%

private Lexico lexico;
private ArrayList<String> lista_variables;
private ArrayList<String> lista_parametros;
private ArrayList<Pair<String,String>> lista_param_invocacion;
private AdmTercetos adminTerceto;
private String ambito;
//private String procedimiento;

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
		Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una constante UINT fuera de rango");
		Main.tSimbolos.eliminarSimbolo(lexema);
	}
	else if (id == Lexico.CTE_DOUBLE) {
		double valor = -1*Double.parseDouble(lexema.replace('d','e'));
		if(( valor > 2.2250738585272014e-308 && valor < 1.7976931348623157e+308) || (valor > -1.7976931348623157e+308 && valor < -2.2250738585072014e-308) || (valor == 0.0))
                	{Main.tSimbolos.modificarSimbolo(lexema, String.valueOf(valor));
                	return true;
                	}
                else {
                	Main.listaErrores.add("Error sint�ctico: Linea " + Lexico.linea + " se detect� una constante DOUBLE fuera de rango");
	               	Main.tSimbolos.eliminarSimbolo(lexema);
	 	}
	}
	return false;
}

public boolean verificarParametros(String proc){
	int orden = 1;
	for(Pair p : lista_param_invocacion){
		String parametroFormal = p.getKey() + "@" + proc;
		String parametroReal = (String)p.getValue();
		if(!Main.tSimbolos.existeLexema(parametroFormal)){ //el usuario lo escribio mal en la invocacion
			Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + "no se reconoce el parametro formal "+ p.getKey());
			return false;}
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getOrden() != orden){
			Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los parametros no estan en el orden correcto");
			return false;}
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getTipo() != Main.tSimbolos.getDatosTabla(parametroReal).getTipo()){
			Main.listaErrores.add("Error semántico: Linea " + Lexico.linea + " los tipos de los parametros reales y formales no son iguales");
			return false;}
		orden++;
	}
	return true;
}
