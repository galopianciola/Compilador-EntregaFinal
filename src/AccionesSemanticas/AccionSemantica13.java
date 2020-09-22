package AccionesSemanticas;

import main.*;

public class AccionSemantica13 extends AccionSemantica {

    @Override
    public Token run() {
        return new Token(Lexico.IGUAL_IGUAL);
        //retorna id del token ==;
    }
}