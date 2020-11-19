package Errores;

import main.AccionSemantica;
import main.Lexico;
import main.Main;
import main.Token;

public class Error12 extends AccionSemantica {

    @Override
    public Token run() {
        Main.listaErrores.add("Error léxico: Linea " + Lexico.linea + " la cadena no se cerro correctamente.");
        return null;
    }
}
