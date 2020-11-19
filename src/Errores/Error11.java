package Errores;

import main.*;

public class Error11 extends AccionSemantica{

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico : Linea "+ Lexico.linea +" se esperaba un salto de linea al terminar el comentario");
        return null;
    }
}