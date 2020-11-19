package Errores;

import main.*;

public class Error7 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea " + Lexico.linea + " se esperaba un '=' después del '!' y llegó otro caracter");
        return null;
    }
}