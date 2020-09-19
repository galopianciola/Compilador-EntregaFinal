package Errores;

import main.*;

public class Error1 extends AccionSemantica {

    @Override
    public void run() {
        System.out.println("Error de compilación en la linea " + Lexico.linea + ": el carácter " + Lexico.caracter + " no es válido");
    }
}