package Errores;

import main.*;

public class Error10 extends AccionSemantica{

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea "+ Lexico.linea +" el lenguaje no reconoce la palabra reservada "+ buffer);
        return null;
    }
}