
package main;

import java.io.*;
import java.util.ArrayList;
import Parser.*;
import CodigoInt.*;

public class Main {

    private static BufferedReader codigo;
    public static TablaSimbolos tSimbolos = new TablaSimbolos();
    public static ArrayList<String> listaErrores = new ArrayList<>();

    private static StringBuilder getCodigo(BufferedReader ubicacion) {
        StringBuilder buffer = new StringBuilder();

        try {
            codigo = new BufferedReader(new FileReader(ubicacion.readLine()));
            String readLine;

            while ((readLine = codigo.readLine()) != null) {
                buffer.append(readLine + "\n");
            }

            buffer.deleteCharAt(buffer.length() - 1);
            //System.out.println(buffer);
            buffer.append("$");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main(String[] args) throws IOException {
        //-------------- CARGA DE ARCHIVO --------------

        InputStreamReader leer = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(leer);
        //System.out.print("Ingrese la direccion del archivo: ");
        // String direccion = buffer.readLine();

        String direccion = "test.txt";
        InputStream is = new ByteArrayInputStream(direccion.getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder codigo = new StringBuilder(getCodigo(br));

        System.out.println("Contenido del archivo: " + codigo);

        AdmTercetos adminTercetos = new AdmTercetos();
        Lexico l1 = new Lexico(codigo);

        System.out.println("\n********* PARSER.RUN() *********");
        Parser p = new Parser(l1, adminTercetos);
        p.run();

        tSimbolos.printTablaSimbolos();

        adminTercetos.generarCodigoIntermedio();
        adminTercetos.printTercetos();
        adminTercetos.printProcedimientos();

        if (listaErrores.isEmpty()) {
            Assembler assembler = new Assembler(adminTercetos);
            assembler.generarAssembler();
        } else {
            listaErrores.add("No se generó el código assembler por haber errores en la generación de código intermedio");
        }

        System.out.println("\n********* Código intermedio *********");
        adminTercetos.printTercetos();

        System.out.println("\n********* TABLA DE SIMBOLOS *********");
        tSimbolos.printTablaSimbolos();

        System.out.println("\n********* ERRORES *********");
        if (listaErrores.isEmpty()) {
            System.out.println("No hubo errores");
        } else {
            for (String s : listaErrores) {
                System.out.println(s);
            }
        }
    }
}