package AccionesSemanticas;

import main.*;

public class AccionSemantica9 extends AccionSemantica {

    @Override
    public Token run() {
        return new Token(Lexico.MENOR_IGUAL);
    }
}