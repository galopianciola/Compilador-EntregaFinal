package Errores;

import main.*;

public class Error1 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error lexico en la linea " + Lexico.linea + ": el carácter " + Lexico.caracter + " no es válido");
        return null;
    }
}