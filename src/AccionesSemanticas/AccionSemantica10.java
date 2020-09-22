package AccionesSemanticas;

import main.*;

public class AccionSemantica10 extends AccionSemantica {

    @Override
    public Token run() {
        Lexico.cursor--;
        return new Token('<');
    }
}