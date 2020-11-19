package Errores;

import main.*;

public class Error8 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea " + Lexico.linea + " no esta permitido un salto de línea antes de un '-' ");
        while (Lexico.caracter != '"') { //mientras no se cierra la cadena
            Lexico.caracter = Lexico.codigoFuente.charAt(Lexico.cursor);
            Lexico.cursor++;
        }
        Lexico.cursor++;
        return null;
    }
}