package AccionesSemanticas;

import main.*;

public class AccionSemantica4 extends AccionSemantica {
    //IDENTIFICADORES

    @Override
    public Token run() {
        Lexico.cursor--;

        if (buffer.length() > 20) {
            buffer = buffer.substring(0, 20);
            System.out.println("Warning: en la linea "+ Lexico.linea +" se truncó el identificador a 20 caracteres");
        }
        if (!Main.tSimbolos.existeLexema(buffer)) {
            Main.tSimbolos.agregarSimbolo(buffer, Lexico.IDE);
        }
        //System.out.println("[Lexico | Linea " + Lexico.linea + "] Se detecto un token de identificador -> " + buffer);
        return new Token(Lexico.IDE, buffer);
    }
}
