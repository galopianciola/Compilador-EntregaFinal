package CodigoInt;

import main.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Assembler {

    private ArrayList<ArrayList<Terceto>> codigoIntermedio;
    private AdmTercetos adminTerceto;
    private boolean EAX;
    private boolean EBX;
    private boolean ECX;
    private boolean EDX;


    public Assembler(AdmTercetos adminTerceto) {
        this.codigoIntermedio = adminTerceto.getCodigoIntermedio();
        this.adminTerceto = adminTerceto;
        this.EAX = true; // Si es true esta libre
        this.EBX = true;
        this.ECX = true;
        this.EDX = true;
    }

    public void generarAssembler() throws IOException {

        String code = this.generarCodeAssembler();
        String data = this.generarDataAssembler();

        FileOutputStream fos = new FileOutputStream(new File("salida.asm"));

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write(".386" + '\n'
                + ".model flat, stdcall" + '\n'
                + "option casemap :none" + '\n'
                + "include \\masm32\\include\\windows.inc" + '\n'
                + "include \\masm32\\include\\kernel32.inc" + '\n'
                + "include \\masm32\\include\\user32.inc" + '\n'
                + "includelib \\masm32\\lib\\kernel32.lib" + '\n'
                + "includelib \\masm32\\lib\\user32.lib" + '\n'
                + '\n' + ".data" + '\n');

        bw.write(data + "\n.code\nstart: \n" + code + "invoke ExitProcess, 0\nend start");

        bw.close();
    }

    public String generarDataAssembler() {
        String data = "";
        Enumeration iterador = Main.tSimbolos.getKeys();
        while (iterador.hasMoreElements()) {
            String lexema = (String) iterador.nextElement();
            switch (Main.tSimbolos.getDatosTabla(lexema).getId()) {
                case (Lexico.IDE):
                    if (!Main.tSimbolos.getDatosTabla(lexema).getUso().equals("nombreProcedimiento")) {
                        if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("UINT"))
                            if (lexema.contains("2bytes"))
                                data = data + "_" + lexema + " DW ?" + '\n';
                            else
                                data = data + "_" + lexema + " DD ?" + '\n';
                        if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("DOUBLE"))
                            data = data + "_" + lexema + " DQ ?" + '\n';
                    }
                    break;
                case (Lexico.CADENA):
                    data = data + "_" + lexema.substring(1, lexema.length() - 1) + " DB " + lexema + ", 0 \n";
                    break;
                // faltan las constantes
                case (Lexico.CTE_DOUBLE):
                    data = data + "_" + lexema + " DQ " + lexema + '\n';
                    break;
                case (Lexico.CTE_UINT):
                    data = data + "_" + lexema + " DD " + lexema + '\n';
                    break;
            }
        }
        return data;
    }

    private String getRegistroVacio() {
        if (EBX) {
            EBX = false;
            return "EBX";
        } else if (ECX) {
            ECX = false;
            return "ECX";
        } else if (EAX) {
            EAX = false;
            return "EAX";
        } else if (EDX) {
            EDX = false;
            return "EDX";
        }
        return null;
    }

    public void marcarRegLibre(String registro) {
        if (registro.equals("EAX"))
            EAX = true;
        if (registro.equals("EBX"))
            EBX = true;
        if (registro.equals("ECX"))
            ECX = true;
        if (registro.equals("EDX"))
            EDX = true;
    }

    public String generarCodeAssembler() {
        String code = "FINIT \n";
        for (ArrayList<Terceto> a : codigoIntermedio) {
            for (Terceto t : a) {
                switch (t.getOperador()) {
                    case "+":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                code += "ADD " + reg + ", _" + t.getOp2() + '\n';
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FADD _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "ADD " + t1.getResultado() + ", _" + t.getOp2() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FADD _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {

                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "ADD " + t1.getResultado() + ", " + t2.getResultado() + '\n';
                                t.setResultado(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FADD _" + t2.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 4a: (OP, var/cte, registro) conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "ADD " + t1.getResultado() + ", _" + t.getOp1() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FADD _" + t1.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");

                            }
                        }

                        break;

                    case "-":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                code += "SUB " + reg + ", _" + t.getOp2() + '\n';
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FSUB _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }

                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "SUB " + t1.getResultado() + ", _" + t.getOp2() + '\n';
                                t.setResultado(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FSUB _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "SUB " + t1.getResultado() + ", " + t2.getResultado() + '\n';
                                t.setResultado(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FSUB _" + t2.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "SUB " + reg + ", " + t1.getResultado() + '\n';
                                t.setResultado(reg);
                                this.marcarRegLibre(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FSUB _" + t1.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        break;

                    case "*":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                EAX = false;
                                code += "MOV EAX, _" + t.getOp1() + '\n';
                                code += "MUL _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FMUL _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                EAX = false;
                                code += "MOV EAX, " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MUL _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FMUL _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                EAX = false;
                                code += "MOV EAX, _" + t1.getResultado() + '\n';
                                code += "MUL _" + t2.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;

                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FMUL _" + t2.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                EAX = false;
                                code += "MOV EAX, _" + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MUL _" + t.getOp1() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV" + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FMUL _" + t1.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        break;

                    case "/":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                EAX = false;
                                EDX = false;
                                code += "MOV EAX, _" + t.getOp1() + '\n';
                                code += "MOV EDX, 0" + '\n';
                                code += "DIV _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                                EDX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FDIV _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                EAX = false;
                                EDX = false;
                                code += "MOV EAX, " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MOV EDX, 0" + '\n';
                                code += "DIV _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                EDX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FDIV _" + t.getOp2() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                EAX = false;
                                EDX = false;
                                code += "MOV EAX, _" + t1.getResultado() + '\n';
                                code += "MOV EDX, 0" + '\n';
                                code += "DIV _" + t2.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                EDX = true;
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FDIV _" + t2.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                EAX = false;
                                EDX = false;
                                code += "MOV EAX, _" + t.getOp1() + '\n';
                                code += "MOV EDX, 0" + '\n';
                                code += "DIV _" + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                EDX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FDIV _" + t1.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                            }
                        }
                        break;

                    case "=":
                        // Situacion a ( = , vble , reg )
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "MOV _" + t.getOp1() + ", " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FST _" + t.getOp1() + '\n';
                            }
                        }
                        // Situacion b ( = , vble , vble )
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                if (Main.tSimbolos.getDatosTabla(t.getOp1()).isParametroRef()) {
                                    EBX = false;
                                    code += "MOV EBX, offset _" + t.getOp2() + '\n';
                                    code += "MOV _" + t.getOp1() + ", EBX \n";
                                    EBX = true;
                                } else {
                                    String reg = this.getRegistroVacio();
                                    code += "MOV " + reg + ", _" + t.getOp2() + '\n';
                                    code += "MOV _" + t.getOp1() + ", " + reg + '\n';
                                    this.marcarRegLibre(reg);
                                }
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp2() + '\n';
                                code += "FST _" + t.getOp1() + '\n';
                            }
                        }
                        break;

                    case "<":
                    case ">":
                    case "==":
                    case ">=":
                    case "<=":
                    case "!=":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp2() + '\n';
                                code += "CMP _" + t.getOp1() + ", " + reg + '\n';
                                this.marcarRegLibre(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FCOM _" + t.getOp2() + '\n';
                                code += "FSTSW _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "MOV AX , _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "SAHF" + '\n';
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");
                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                code += "CMP " + t1.getResultado() + ", _" + t.getOp2() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FCOM _" + t.getOp2() + '\n';
                                code += "FSTSW _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "MOV AX , _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "SAHF" + '\n';
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");
                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));
                                code += "CMP " + t1.getResultado() + ", " + t2.getResultado() + '\n';

                                this.marcarRegLibre(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FCOM _" + t2.getResultado() + '\n';
                                code += "FSTSW _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "MOV AX , _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "SAHF" + '\n';
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");
                            }
                        }
                        //situacion 3: (operador, var/cte, registro)
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                code += "CMP _" + t.getOp1() + ", " + t1.getResultado() + '\n';

                                this.marcarRegLibre(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                code += "FLD _" + t.getOp1() + '\n';
                                code += "FCOM _" + t1.getResultado() + '\n';
                                code += "FSTSW _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "MOV AX , _" + "var" + t.getNumero() + "_2bytes" + '\n';
                                code += "SAHF" + '\n';
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");
                            }
                        }
                        break;

                    case "BF":
                        String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                        Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                        code += this.tipoSalto(t1.getOperador()) + " Label" + t.getOp2() + '\n';
                        break;

                    case "BI":
                        code += "JMP Label" + t.getOp1() + '\n';
                        break;

                    case "OUT":
                        String cadena = t.getOp1().substring(1, t.getOp1().length() - 1);
                        code += "invoke MessageBox, NULL, addr _" + cadena + ", addr _" + cadena + ", MB_OK \n";
                        break;

                    case "ComienzaProc":
                        code += t.getOp1() + ": \n";
                        break;

                    case "FinProc":
                        code += "RET \n";
                        break;

                    case "INV":
                        code += "CALL " + t.getOp1() + "\n";
                        break;

                    case "CONV":
                        if (t.esVariable(1)){
                            code += "FILD _" + t.getOp1() + '\n';
                            code += "FST _var" + t.getNumero() + '\n';
                            t.setResultado("var" + t.getNumero());
                            Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");

                        } else {

                            String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                            Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                            code += "MOV _varAux" + t.getNumero() + ", " + t2.getResultado() + '\n';
                            code += "FILD _varAux" + t.getNumero()+ '\n';
                            code += "FST _var" + t.getNumero() + '\n';
                            t.setResultado("var" + t.getNumero());
                            Main.tSimbolos.agregarSimbolo("varAux" + t.getNumero(), Lexico.IDE, "UINT", "variable");
                            Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");
                        }
                        break;

                    default: //Para terceto (Label..., , )
                        code += t.getOperador() + ": \n";
                        break;
                }
            }
        }
        code += "FINIT\n";
        return code;
    }

    private String tipoSalto(String comparador) {
        switch (comparador) {
            case "==":
                return "JNE";
            case "!=":
                return "JE";
            case "<":
                return "JAE";
            case "<=":
                return "JA";
            case ">":
                return "JBE";
            case ">=":
                return "JB";
        }
        return null;
    }
}
