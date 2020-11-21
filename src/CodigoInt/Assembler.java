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
    private static final int limiteSuperiorUint = 65535;
    private static final int limiteInferiorUint = 0;
    private static final double limiteInferiorDoublePositivo = 2.2250738585272014d-308;
    private static final double limiteSuperiorDoublePositivo = 1.7976931348623157d+308;
    private static final double limiteInferiorDoubleNegativo = -1.7976931348623157d+308;
    private static final double limiteSuperiorDoubleNegativo = -2.2250738585072014d-308;
    private static final double limiteDoubleCero = 0.0;

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
        data += "_limiteSuperiorUint DD " + limiteSuperiorUint + '\n';
        data += "_limiteInferiorUint DD " + limiteInferiorUint + '\n';
        data += "_limiteInferiorDoublePositivo DQ " + limiteInferiorDoublePositivo + '\n';
        data += "_limiteSuperiorDoublePositivo DQ " + limiteSuperiorDoublePositivo + '\n';
        data += "_limiteInferiorDoubleNegativo DQ " + limiteInferiorDoubleNegativo + '\n';
        data += "_limiteSuperiorDoubleNegativo DQ " + limiteSuperiorDoubleNegativo + '\n';
        data += "_limiteDoubleCero DQ " + limiteDoubleCero + '\n';
        data += "_OverflowSuma DB \"Overflow en suma\", 0 \n";
        data += "_ResNegativoRestaUint DB \"Resultado negativo en resta entero sin signo\", 0 \n";
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

                case (Lexico.CTE_DOUBLE):
                    String nombre = lexema.replace('.', '_');
                    nombre = nombre.replace('-', '_');
                    nombre = nombre.replace("+", "__");
                    data = data + "_" + nombre + " DQ " + lexema + '\n';
                    break;
                case (Lexico.CTE_UINT):
                    data = data + "_" + lexema + " DD " + lexema + '\n';
                    break;
            }
        }
        return data;
    }

    private String getRegistroVacio() {
        if (ECX) {
            ECX = false;
            return "ECX";
        } else if (EBX) {
            EBX = false;
            return "EBX";
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

    private boolean esUnPuntero(String variable){
        return Main.tSimbolos.getDatosTabla(variable).isParametroRef();
    }

    public String generarCodeAssembler() {
        String code = "FINIT \n";
        String procActual = "main";
        for (ArrayList<Terceto> a : codigoIntermedio) {
            for (Terceto t : a) {
                switch (t.getOperador()) {
                    case "+":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "MOV " + reg + ", dword ptr [EBX] \n";
                                    code += "ADD " + reg + ", _" + t.getOp2() + '\n';
                                } else if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() + '\n';
                                    code += "MOV " + reg + ", dword ptr [EBX] \n";
                                    code += "ADD " + reg + ", _" + t.getOp1() + '\n';
                                } else {
                                    code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                    code += "ADD " + reg + ", _" + t.getOp2() + '\n';
                                }
                                code += "CMP " + reg + ", _limiteSuperiorUint" + '\n';
                                code += "JA " + "LabelOverflowSuma" + '\n';
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                code += "FLD _" + op1 + '\n';
                                code += "FADD _" + op2 + '\n';
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
                                if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() +'\n';
                                    code += "ADD " + t1.getResultado() + ", dword ptr [EBX] \n";
                                } else {
                                    code += "ADD " + t1.getResultado() + ", _" + t.getOp2() + '\n';
                                }
                                code += "CMP " + t1.getResultado() + ", _limiteSuperiorUint" + '\n';
                                code += "JA " + "LabelOverflowSuma" + '\n';
                                t.setResultado(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op2 = t.getOp2();
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FADD _" + op2 + '\n';
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
                                code += "CMP " + t1.getResultado() + ", _limiteSuperiorUint" + '\n';
                                code += "JA " + "LabelOverflowSuma" + '\n';
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
                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() +'\n';
                                    code += "ADD " + t1.getResultado() + ", dword ptr [EBX] \n";
                                } else {
                                    code += "ADD " + t1.getResultado() + ", _" + t.getOp1() + '\n';
                                }
                                code += "CMP " + t1.getResultado() + ", _limiteSuperiorUint" + '\n';
                                code += "JA " + "LabelOverflowSuma" + '\n';
                                t.setResultado(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + op1 + '\n';
                                code += "FADD _" + t1.getResultado() + '\n';
                                code += "FST _" + "var" + t.getNumero() + '\n';
                                t.setResultado("var" + t.getNumero());
                                Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");

                            }
                        }
                        if(t.getTipo().equals("DOUBLE")) {
                            code += "FLD _var" + t.getNumero() + '\n';
                            code += "FCOM _limiteInferiorDoublePositivo \n";
                            code += "FSTSW _" + "var1_" + t.getNumero() + "_2bytes" + '\n';
                            code += "MOV AX , _" + "var1_" + t.getNumero() + "_2bytes" + '\n';
                            code += "SAHF" + '\n';
                            code += "JA LabelLimiteSupPositivo \n";
                            code += "JBE LabelLimiteInfNegativo \n";
                            Main.tSimbolos.agregarSimbolo("var1_" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");

                            code += "LabelLimiteSupPositivo: \n";
                            code += "FLD _var" + t.getNumero() + '\n';
                            code += "FCOM _limiteSuperiorDoublePositivo \n";
                            code += "FSTSW _" + "var2_" + t.getNumero() + "_2bytes" + '\n';
                            code += "MOV AX , _" + "var2_" + t.getNumero() + "_2bytes" + '\n';
                            code += "SAHF" + '\n';
                            code += "JB LabelNoOverflow \n";
                            code += "JAE LabelOverflowSuma \n";
                            Main.tSimbolos.agregarSimbolo("var2_" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");


                            code += "LabelLimiteInfNegativo: \n";
                            code += "FLD _var" + t.getNumero() + '\n';
                            code += "FCOM _limiteInferiorDoubleNegativo \n";
                            code += "FSTSW _" + "var3_" + t.getNumero() + "_2bytes" + '\n';
                            code += "MOV AX , _" + "var3_" + t.getNumero() + "_2bytes" + '\n';
                            code += "SAHF" + '\n';
                            code += "JA LabelLimiteSupNegativo \n";
                            code += "JBE LabelOverflowSuma \n";
                            Main.tSimbolos.agregarSimbolo("var3_" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");


                            code += "LabelLimiteSupNegativo: \n";
                            code += "FLD _var" + t.getNumero() + '\n';
                            code += "FCOM _limiteSuperiorDoubleNegativo \n";
                            code += "FSTSW _" + "var4_" + t.getNumero() + "_2bytes" + '\n';
                            code += "MOV AX , _" + "var4_" + t.getNumero() + "_2bytes" + '\n';
                            code += "SAHF" + '\n';
                            code += "JB LabelNoOverflow \n";
                            code += "JAE LabelCero \n";
                            Main.tSimbolos.agregarSimbolo("var4_" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");


                            code += "LabelCero: \n";
                            code += "FLD _var" + t.getNumero() + '\n';
                            code += "FCOM _limiteDoubleCero \n";
                            code += "FSTSW _" + "var5_" + t.getNumero() + "_2bytes" + '\n';
                            code += "MOV AX , _" + "var5_" + t.getNumero() + "_2bytes" + '\n';
                            code += "SAHF" + '\n';
                            code += "JNE LabelOverflowSuma \n";
                            Main.tSimbolos.agregarSimbolo("var5_" + t.getNumero() + "_2bytes", Lexico.IDE, "UINT", "variable");

                            code+= "LabelNoOverflow: \n";

                        }


                        break;

                    case "-":
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();

                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "MOV " + reg + ", dword ptr [EBX] \n";
                                    code += "SUB " + reg + ", _" + t.getOp2() + '\n';
                                } else if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() + '\n';
                                    code += "MOV " + reg + ", _" + t.getOp1() + "\n";
                                    code += "SUB " + reg + ", dword ptr [EBX] \n";
                                } else {
                                    code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                    code += "ADD " + reg + ", _" + t.getOp2() + '\n';
                                }

                                code += "CMP " + reg + ", _limiteInferiorUint" + '\n';
                                code += "JS " + "LabelRestaNegativa" + '\n';
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");
                                code += "FLD _" + op1 + '\n';
                                code += "FSUB _" + op2 + '\n';
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

                                if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() +'\n';
                                    code += "SUB " + t1.getResultado() + ", dword ptr [EBX] \n";
                                } else {
                                    code += "SUB " + t1.getResultado() + ", _" + t.getOp2() + '\n';
                                }

                                code += "CMP " + t1.getResultado() + ", _limiteInferiorUint" + '\n';
                                code += "JS " + "LabelRestaNegativa" + '\n';
                                t.setResultado(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op2 = t.getOp2();
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FSUB _" + op2 + '\n';
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
                                code += "CMP " + t1.getResultado() + ", _limiteInferiorUint" + '\n';
                                code += "JS " + "LabelRestaNegativa" + '\n';
                                t.setResultado(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                            }
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

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() +'\n';
                                    code += "MOV " + reg + ", dword ptr [EBX] \n";
                                } else {
                                    code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                }

                                code += "SUB " + reg + ", " + t1.getResultado() + '\n';
                                code += "CMP " + reg + ", _limiteInferiorUint" + '\n';
                                code += "JS " + "LabelRestaNegativa" + '\n';
                                t.setResultado(reg);
                                this.marcarRegLibre(t1.getResultado());
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + op1 + '\n';
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
                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "MOV EAX, dword ptr [EBX] \n";
                                    code += "MUL _" + t.getOp2() + '\n';
                                } else
                                if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() + '\n';
                                    code += "MOV EAX, dword ptr [EBX] \n";
                                    code += "MUL _" + t.getOp1() + '\n';
                                } else {
                                    code += "MOV EAX, _" + t.getOp1() + '\n';
                                    code += "MUL _" + t.getOp2() + '\n';
                                }

                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                code += "FLD _" + op1 + '\n';
                                code += "FMUL _" + op2 + '\n';
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

                                if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() +'\n';
                                    code += "MOV EAX, " + t1.getResultado() + '\n';
                                    code += "MUL dword ptr [EBX] \n";
                                } else {
                                    code += "MOV EAX, " + t1.getResultado() + '\n';
                                    code += "MUL _" + t.getOp2() + '\n';
                                }

                                this.marcarRegLibre(t1.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op2 = t.getOp2();
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FMUL _" + op2 + '\n';
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

                            }
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

                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() +'\n';
                                    code += "MOV EAX, _" + t1.getResultado() + '\n';
                                    code += "MUL dword ptr [EBX] \n";
                                } else {
                                    code += "MOV EAX, _" + t1.getResultado() + '\n';
                                    code += "MUL _" + t.getOp1() + '\n';
                                }

                                this.marcarRegLibre(t1.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV" + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

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

                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "MOV EAX, dword ptr [EBX] \n";
                                    code += "MOV EDX, 0" + '\n';
                                    code += "DIV _" + t.getOp2() + '\n';
                                } else if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() + '\n';
                                    code += "MOV EAX, _" + t.getOp1() + '\n';
                                    code += "MOV EDX, 0" + '\n';
                                    code += "DIV dword ptr [EBX] \n";
                                } else {
                                    code += "MOV EAX, _" + t.getOp1() + '\n';
                                    code += "MOV EDX, 0" + '\n';
                                    code += "DIV _" + t.getOp2() + '\n';
                                }

                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                t.setResultado(reg);
                                EAX = true;
                                EDX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");
                                code += "FLD _" + op1 + '\n';
                                code += "FDIV _" + op2 + '\n';
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

                                if(esUnPuntero(t.getOp2())){
                                    code += "MOV EBX, _" + t.getOp2() +'\n';
                                    code += "MOV EAX, " + t1.getResultado() + '\n';
                                    code += "MOV EDX, 0 \n";
                                    code += "DIV dword ptr [EBX] \n";
                                } else {
                                    code += "MOV EAX, " + t1.getResultado() + '\n';
                                    code += "MOV EDX, 0" + '\n';
                                    code += "DIV _" + t.getOp2() + '\n';
                                }

                                this.marcarRegLibre(t1.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", EAX" + '\n';
                                EAX = true;
                                EDX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op2 = t.getOp2();
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FDIV _" + op2 + '\n';
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
                            }
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

                                if(esUnPuntero(t.getOp1())){
                                    code += "MOV EBX, _" + t.getOp1() +'\n';
                                    code += "MOV EAX, dword ptr [EBX] \n";
                                } else {
                                    code += "MOV EAX, _" + t.getOp1() + '\n';
                                }

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
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + op1 + '\n';
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
                                if(esUnPuntero(t.getOp1())){
                                    String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                    Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                    EBX = false;
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "MOV dword ptr [EBX], " + t1.getResultado() + '\n';
                                    EBX = true;
                                    this.marcarRegLibre(t1.getResultado());
                                } else {
                                    String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                    Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                    code += "MOV _" + t.getOp1() + ", " + t1.getResultado() + '\n';
                                    this.marcarRegLibre(t1.getResultado());
                                }
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "FLD _" + t1.getResultado() + '\n';
                                code += "FST _" + op1 + '\n';
                            }
                        }
                        // Situacion b ( = , vble , vble )
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String paramProc = t.getOp1().substring(t.getOp1().indexOf("@")+1);
                                if (esUnPuntero(t.getOp1()) && !paramProc.equals(procActual)) {
                                    EBX = false;
                                    code += "MOV EBX, offset _" + t.getOp2() + '\n';
                                    code += "MOV _" + t.getOp1() + ", EBX \n";
                                    EBX = true;
                                } else if(esUnPuntero(t.getOp1()) && paramProc.equals(procActual)) {
                                        EBX = false;
                                        String reg = this.getRegistroVacio();
                                        code += "MOV EBX, _" + t.getOp1() + '\n';
                                        code += "MOV " + reg + ", _" + t.getOp2() +'\n';
                                        code += "MOV dword ptr [EBX], " + reg + '\n';
                                        EBX = true;
                                        this.marcarRegLibre(reg);
                                } else if(esUnPuntero(t.getOp2())) {
                                        String reg = this.getRegistroVacio();
                                        code += "MOV EBX, _" + t.getOp2() + '\n';
                                        code += "MOV " + reg + ", dword ptr [EBX] \n";
                                        code += "MOV _" + t.getOp1() + ", " + reg + '\n';
                                        this.marcarRegLibre(reg);
                                } else {
                                        String reg = this.getRegistroVacio();
                                        code += "MOV " + reg + ", _" + t.getOp2() + '\n';
                                        code += "MOV _" + t.getOp1() + ", " + reg + '\n';
                                        this.marcarRegLibre(reg);
                                    }
                            }
                            if (t.getTipo().equals("DOUBLE")) {
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                String paramProc = t.getOp1().substring(t.getOp1().indexOf("@") + 1);
                                if (esUnPuntero(t.getOp1()) && !paramProc.equals(procActual)) {
                                    EBX = false;
                                    code += "MOV EBX, offset _" + op2 + '\n';
                                    code += "MOV _var" + t.getNumero() + ", EBX \n";
                                    EBX = true;
                                    code += "FILD _var" + t.getNumero() + '\n';
                                    code += "FST _" + op1 + '\n';
                                    Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "UINT", "variable");
                                } else if(esUnPuntero(t.getOp1()) && paramProc.equals(procActual)) {
                                    code += "MOV EBX, _" + t.getOp1() + '\n';
                                    code += "FLD _" + op2 + '\n';
                                    code += "FST dword ptr [EBX] \n";
                                    //code += "FST _var" + t.getNumero() + "\n";
                                    //code += "MOV dword ptr [EBX], _var" + t.getNumero() + '\n';
                                    //Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "UINT", "variable");
                                } else if (esUnPuntero(t.getOp2())) {
                                    String reg = this.getRegistroVacio();
                                    code += "MOV EBX, _" + t.getOp2() + '\n';
                                    code += "MOV _var"+ t.getNumero() + ", dword ptr [EBX] \n";
                                    code += "FLD _var" + t.getNumero() + '\n';
                                    code += "FST _" + op1 + '\n';
                                    Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "UINT", "variable");
                                } else {
                                    code += "FLD _" + op2 + '\n';
                                    code += "FST _" + op1 + '\n';
                                }
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
                                String op1 = t.getOp1();
                                String op2 = t.getOp2();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

                                code += "FLD _" + op1 + '\n';
                                code += "FCOM _" + op2 + '\n';
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
                                String op2 = t.getOp2();
                                op2 = t.getOp2().replace('.','_');
                                op2 = op2.replace('-','_');
                                op2 = op2.replace("+","__");

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
                                String op1 = t.getOp1();
                                op1 = t.getOp1().replace('.','_');
                                op1 = op1.replace('-','_');
                                op1 = op1.replace("+","__");

                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));

                                code += "FLD _" + op1 + '\n';
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
                        if(procActual.equals("main")){
                            code += "FINIT\n";
                            code += "invoke ExitProcess, 0 \n";
                        }
                        code += t.getOp1() + ": \n";
                        procActual = t.getOp1().substring(0, t.getOp1().indexOf("@"));
                        break;

                    case "FinProc":
                        code += "RET \n";
                        break;

                    case "INV":
                        code += "CALL " + t.getOp1() + "\n";
                        break;

                    case "CONV":
                        if (t.esVariable(1)) {
                            code += "FILD _" + t.getOp1() + '\n';
                            code += "FST _var" + t.getNumero() + '\n';
                            t.setResultado("var" + t.getNumero());
                            Main.tSimbolos.agregarSimbolo("var" + t.getNumero(), Lexico.IDE, "DOUBLE", "variable");

                        } else {
                            String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                            Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));
                            code += "MOV _varAux" + t.getNumero() + ", " + t2.getResultado() + '\n';
                            code += "FILD _varAux" + t.getNumero() + '\n';
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
        code += "invoke ExitProcess, 0 \n";
        code += "LabelRestaNegativa: \n";
        code += "invoke MessageBox, NULL, addr _ResNegativoRestaUint, addr _ResNegativoRestaUint, MB_OK \n";
        code += "FINIT\n";
        code += "invoke ExitProcess, 0 \n";
        code += "LabelOverflowSuma: \n";
        code += "invoke MessageBox, NULL, addr _OverflowSuma, addr _OverflowSuma, MB_OK \n";
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
