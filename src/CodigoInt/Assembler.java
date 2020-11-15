package CodigoInt;

import main.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Assembler {

    private ArrayList<ArrayList<Terceto>> codigoIntermedio;
    private AdmTercetos adminTerceto;
    private boolean AX;
    private boolean BX;
    private boolean CX;
    private boolean DX;


    public Assembler(AdmTercetos adminTerceto) {
        this.codigoIntermedio = adminTerceto.getCodigoIntermedio();
        this.adminTerceto = adminTerceto;
        this.AX = true; // Si es true esta libre
        this.BX = true;
        this.CX = true;
        this.DX = true;
    }

    public void generarAssembler() throws IOException {
        String assembler = "";

        String code = this.generarCodeAssembler();
        String data = this.generarDataAssembler();

        //assembler = ".data \n" + data + "\n code \n" + code;

        //System.out.println("Assembler : \n" + assembler);


        //File archivo = new File("salida.asm");

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
                + '\n' +".data" + '\n');

        bw.write(data + "\n.code\nstart: \n" + code + "invoke ExitProcess, 0\nend start");

        bw.close();

//		PrintWriter p = new PrintWriter(new FileWriter(arch));
        //Imprimir codigo assembler

    /*    String comc = "cmd /c .\\masm32\\bin\\ml /c /Zd /coff salida.asm";
        Process ptasm32 = Runtime.getRuntime().exec(comc);
        InputStream is = ptasm32.getInputStream();

        String coml = "cmd /c \\masm32\\bin\\Link /SUBSYSTEM:CONSOLE salida.obj ";
        Process ptlink32 = Runtime.getRuntime().exec(coml);
        InputStream is2= ptlink32.getInputStream();*/
    }

   /* public void writeFile1(File archivo) throws IOException {
        FileOutputStream fos = new FileOutputStream(archivo);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));


        bw.write(".386" + '\n'
                + ".model flat, stdcall" + '\n'
                + "option casemap :none" + '\n'
                + "include \\masm32\\include\\windows.inc" + '\n'
                + "include \\masm32\\include\\kernel32.inc" + '\n'
                + "include \\masm32\\include\\user32.inc" + '\n'
                + "includelib \\masm32\\lib\\kernel32.lib" + '\n'
                + "includelib \\masm32\\lib\\user32.lib" + '\n'
                + '\n' +".data" + '\n');

        String aux = this.generarCodeAssembler();
        String data = this.generarDataAssembler();

        // data = data + controladorTercetos.getPrintsAssembler();

        data = data + '\n' + ".code";

        bw.write( data );

        //Inicia el codigo
        String code = "start:" + '\n' + aux;

        code = code + "invoke ExitProcess, 0" + '\n';

        bw.write( code );

        bw.write( "end start" );
        bw.close();
    }*/

    public String generarDataAssembler() {
        String data = "";
        Enumeration iterador = Main.tSimbolos.getKeys();
        while (iterador.hasMoreElements()) {
            String lexema = (String) iterador.nextElement();
            switch (Main.tSimbolos.getDatosTabla(lexema).getId()) {
                case (Lexico.IDE):
                    if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("UINT"))
                        data = data + "_" + lexema + " DW ?" + '\n';
                    if (Main.tSimbolos.getDatosTabla(lexema).getTipo().equals("DOUBLE"))
                        data = data + "_" + lexema + " DQ ?" + '\n';
                    break;
                case (Lexico.CADENA):
                    data = data + "_" + lexema + " DQ ?" + '\n';
                    break;
                // faltan las constantes
                case (Lexico.CTE_DOUBLE):
                    data = data + "_" + lexema + " DQ " + lexema + '\n';
                    break;
                case (Lexico.CTE_UINT):
                    data = data + "_" + lexema + " DW " + lexema + '\n';
                    break;
            }
        }
        return data;
    }

    private String getRegistroVacio() {
            if (BX) {
                BX = false;
                return "BX";
            } else if (CX) {
                CX = false;
                return "CX";
            } else if (AX) {
                AX = false;
                return "AX";
            } else if (DX) {
                DX = false;
                return "DX";
            }
            return null;
        }
        /*if(operacion == "*" || operacion == "/"){
            if(AX) {
                AX = false;
                return "AX";
            } else if (DX) {
                DX = false;
                return "DX";
            }
        }*/


    public void marcarRegLibre(String registro) {
        if (registro.equals("AX"))
            AX = true;
        if (registro.equals("BX"))
            BX = true;
        if (registro.equals("CX"))
            CX = true;
        if (registro.equals("DX"))
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
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                code += "ADD " + reg + ", _" + t.getOp2() + '\n';
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

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
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

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

                            }
                        }

                        break;

                    case ("-"):
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", _" + t.getOp1() + '\n';
                                code += "SUB " + reg + ", _" + t.getOp2() + '\n';
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

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

                            }
                        }
                        break;

                    case ("*"):
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                AX = false;
                                code += "MOV AX, _" + t.getOp1() + '\n';
                                code += "MUL _" + t.getOp2()+ '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                AX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                AX = false;
                                code += "MOV AX, " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MUL _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                t.setResultado(reg);
                                AX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                AX = false;
                                code += "MOV AX, _" + t1.getResultado() + '\n';
                                code += "MUL _" + t2.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                t.setResultado(reg);
                                AX = true;

                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                AX = false;
                                code += "MOV AX, _" + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MUL _" + t.getOp1() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV" + reg + ", AX" + '\n';
                                t.setResultado(reg);
                                AX = true;
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        break;

                    case ("/"):
                        //situacion 1: (operador, var/cte, var/cte)
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                AX = false;
                                DX = false;
                                code += "MOV AX, _" + t.getOp1() + '\n';
                                code += "MOV DX, 0" + '\n';
                                code += "DIV _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                t.setResultado(reg);
                                AX = true;
                                DX = true;
                            }
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 2: (operador, registro, var/cte)
                        if (!t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                AX = false;
                                DX = false;
                                code += "MOV AX, " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                code += "MOV DX, 0" + '\n';
                                code += "DIV _" + t.getOp2() + '\n';
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                AX = true;
                                DX = true;
                                t.setResultado(reg);

                            }
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 3: (operador, registro, registro)
                        if (!t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto1 = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto1));

                                String nroTerceto2 = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t2 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto2));

                                AX = false;
                                DX = false;
                                code += "MOV AX, _" + t1.getResultado() + '\n';
                                code += "MOV DX, 0" + '\n';
                                code += "DIV _" + t2.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                this.marcarRegLibre(t2.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                AX = true;
                                DX = true;
                                t.setResultado(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        //situacion 4b: (OP, var/cte, registro) no conmutativa
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                AX = false;
                                DX = false;
                                code += "MOV AX, _" + t.getOp1() + '\n';
                                code += "MOV DX, 0" + '\n';
                                code += "DIV _" + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg + ", AX" + '\n';
                                AX = true;
                                DX = true;
                                t.setResultado(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        break;

                    case ("="):
                        // Situacion a ( = , vble , reg )
                        if (t.esVariable(1) && !t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String nroTerceto = t.getOp2().substring(1, t.getOp2().lastIndexOf("]"));
                                Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                                code += "MOV _" + t.getOp1() + ", " + t1.getResultado() + '\n';
                                this.marcarRegLibre(t1.getResultado());
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

                            }
                        }
                        // Situacion b ( = , vble , vble )
                        if (t.esVariable(1) && t.esVariable(2)) {
                            if (t.getTipo().equals("UINT")) {
                                String reg = this.getRegistroVacio();
                                code += "MOV " + reg +  ", _" + t.getOp2()+ '\n';
                                code += "MOV _" + t.getOp1() + ", " + reg + '\n';
                                this.marcarRegLibre(reg);
                            } // Que pasa si no hay registros libres ???
                            if (t.getTipo().equals("DOUBLE")) {

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
                                code += "MOV "+ reg + ", _"+t.getOp2() + '\n';
                                code += "CMP _" + t.getOp1() + ", " + reg + '\n';
                                this.marcarRegLibre(reg);
                            }
                            if (t.getTipo().equals("DOUBLE")) {

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

                            }
                        }
                        break;

                    case "BF":
                        String nroTerceto = t.getOp1().substring(1, t.getOp1().lastIndexOf("]"));
                        Terceto t1 = adminTerceto.getTerceto(Integer.parseInt(nroTerceto));
                        code += this.tipoSalto(t1.getOperador()) + " Label" + t.getOp2() +'\n';

                        break;

                    case "BI":
                        code += "JMP Label" + t.getOp1() + '\n';
                        break;

                    default: //Para terceto (Label..., , )
                        code += t.getOperador() +": \n";
                        break;
                }

            }
        }
        return code;
    }

    private String tipoSalto(String comparador) {
        switch (comparador){
            case "==":
                return "JE";
            case "!=":
                return "JNE";
            case "<":
                return "JB";
            case"<=":
                return "JBE";
            case">":
                return "JA";
            case">=":
                return "JAE";
        }
        return null;
    }
}
