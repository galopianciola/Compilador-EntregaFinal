package AccionesSemanticas;

import main.*;

public class AccionSemantica9 extends AccionSemantica {

    @Override
    public Token run() {
        System.out.println("[Lexico | Linea " + Lexico.linea + "] Se detecto el token '<='");
        return new Token(Lexico.MENOR_IGUAL);
    }
}