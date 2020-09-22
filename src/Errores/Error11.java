package Errores;

import main.*;

public class Error11 extends AccionSemantica {
    @Override
    public Token run() {
        System.out.println("Error lexico en la linea " + Lexico.linea + ", la constante se encuentra fuera de rango ");
        return null;
    }
}
