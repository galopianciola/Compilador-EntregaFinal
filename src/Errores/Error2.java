package Errores;

import main.*;

public class Error2 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error lexico en la linea " + Lexico.linea + ": se esperaba un dígito, '_' ó '.' y llegó otro carácter");
        return null;
    }
}