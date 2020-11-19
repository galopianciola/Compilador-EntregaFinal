package Errores;

import main.*;

public class Error6 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea " + Lexico.linea + " se esperaba '+' ó '-' y llegó otro caracter");
        return null;
    }
}