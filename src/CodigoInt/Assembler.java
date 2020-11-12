package CodigoInt;
import main.*;

import java.util.ArrayList;
import java.util.Enumeration;

public class Assembler {

    private ArrayList<ArrayList<Terceto>> codigoIntermedio;
    private AdmTercetos adminTerceto;
    private boolean AX;
    private boolean BX;
    private boolean CX;
    private boolean DX;

    public Assembler(ArrayList<ArrayList<Terceto>> codigoIntermedio, AdmTercetos adminTerceto){
        this.codigoIntermedio = codigoIntermedio;
        this.adminTerceto = adminTerceto;
        this.AX = true; // Si es true esta libre
        this.BX = true;
        this.CX = true;
        this.DX = true;
    }

    public void generarAssembler(){
        String assembler = "";
    }

    public String generarDataAssembler() {
        String data = "";
        Enumeration iterador = Main.tSimbolos.getKeys();
        while (iterador.hasMoreElements()) {
            String lexema = (String) iterador.nextElement();
            switch (Main.tSimbolos.getDatosTabla(lexema).getId()) {
                case (Lexico.IDE): {
                    if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("UINT"))
                        data = data + "_" + lexema + " DW ?" + '\n';
                    if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("DOUBLE"))
                        data = data + "_" + lexema + " DQ ?" + '\n';
                    break;
                }
                case (Lexico.CADENA): {
                    data = data + "_" + lexema + " DQ ?" + '\n';
                    break;
                }
                // faltan las constantes

            }
        }
        return data;
    }

    private String getRegistroVacio(String operacion) {
        if(operacion == "+" || operacion == "-" || operacion == "="){
            if(BX) {
                BX = false;
                return "BX";
            } else if(CX) {
                CX = false;
                return "CX";
                } else if(AX) {
                    AX = false;
                    return "AX";
                    } else if (DX) {
                        DX = false;
                        return "DX";
                        }
            }
        if(operacion == "*" || operacion == "/"){
            if(AX) {
                AX = false;
                return "AX";
            } else if (DX) {
                DX = false;
                return "DX";
            }
        }
        return null;
    }

    public void marcarRegLibre(String registro){
        if(registro.equals("AX"))
            AX = true;
        if(registro.equals("BX"))
            BX = true;
        if(registro.equals("CX"))
            CX = true;
        if(registro.equals("DX"))
            DX = true;
    }

    public String generarCodeAssembler() {
        String code = "";
        for (ArrayList<Terceto> a : codigoIntermedio) {
            for (Terceto t : a) {
                switch (t.getOperador()) {
                    case ("+"):
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio("+");
                                code += "MOV " + reg + " " + t.getOp1() + '\n';
                                code += "ADD " + reg + " " + t.getOp2() + '\n';
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "ADD " + t1.getResultado() + " " + t.getOp2() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "ADD " + t1.getResultado() + " " + t2.getResultado() + '\n';
                                t.setResultado(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 4a: (OP, var/cte, registro) conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "ADD " + t1.getResultado() + " " + t.getOp1() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }

                        return code;

                    case ("-"):
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio("+");
                                code += "MOV " + reg + " " + t.getOp1() + '\n';
                                code += "SUB " + reg + " " + t.getOp2() + '\n';
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }

                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "SUB " + t1.getResultado() + " " + t.getOp2() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                                }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "SUB " + t1.getResultado() + " " + t2.getResultado() + '\n';
                                t.setResultado(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio("-");
                                code += "MOV "+ reg + " " + t.getOp1() + '\n';
                                String nroTerceto = t.getOp2().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "SUB " + reg + " " + t1.getResultado() + '\n';
                                t.setResultado(reg);
                                this.marcarRegLibre(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        return code;

                    case("*"):
                        return code;
                    case("/"):
                        return code;

                    case("="):
                        // Situacion a ( = , vble , reg )
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "MOV " + t.getOp1() + " " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }

                        // Situacion b ( = , vble , vble )
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio("-");
                                code += "MOV " + reg + " " + t.getOp2() + '\n';
                                code += "MOV " + t.getOp1() + " " + reg + '\n';
                                this.marcarRegLibre(reg);
                            } // Que pasa si no hay registros libres ???
                            if (Main.tSimbolos.getDatosTabla(t.getOp1()).getTipo().equals("DOUBLE")) {

                            }
                        }
                        return code;
                }

            }
        }
    }



}
