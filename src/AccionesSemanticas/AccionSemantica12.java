package AccionesSemanticas;

import main.*;

public class AccionSemantica12 extends AccionSemantica {

    @Override
    public Token run() {
        Lexico.cursor--;
        return new Token('>');
        //retorna id del token >;
    }
}