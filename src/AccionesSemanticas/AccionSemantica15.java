package AccionesSemanticas;

import main.*;

public class AccionSemantica15 extends AccionSemantica {

    @Override
    public Token run() {
        return new Token(Lexico.DISTINTO);
        //retornar id del token '!='
    }


}

