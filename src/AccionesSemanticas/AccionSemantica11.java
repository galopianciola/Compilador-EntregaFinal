package AccionesSemanticas;

import main.*;

public class AccionSemantica11 extends AccionSemantica {

    @Override
    public Token run() {
        return new Token(Lexico.MAYOR_IGUAL);
    }
}