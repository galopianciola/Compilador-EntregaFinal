package AccionesSemanticas;

import main.*;

public class AccionSemantica7 extends AccionSemantica {
    //CARACTERES SIMPLES + - ( )

    @Override
    public Token run() {
        System.out.println("[Lexico | Linea " + Lexico.linea + "] Se detecto el token -> " + Lexico.caracter);
        return new Token(Lexico.caracter);
    }
}