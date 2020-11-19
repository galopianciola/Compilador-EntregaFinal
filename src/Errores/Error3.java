package Errores;

import main.*;

public class Error3 extends AccionSemantica {

    @Override
    public Token run() {
        Lexico.cursor--;
        Main.listaErrores.add("Error lexico en la linea " + Lexico.linea + ": se esperaba una 'u' y llegó el carácter " + Lexico.caracter);
        return null;
    }
}