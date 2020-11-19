package Errores;

import main.*;

public class Error5 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea " + Lexico.linea + " se esperaba un dígito y llegó el carácter " + Lexico.caracter);
        return null;
    }
}