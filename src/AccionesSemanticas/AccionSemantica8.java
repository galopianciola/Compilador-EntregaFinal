package AccionesSemanticas;

import main.*;

public class AccionSemantica8 extends AccionSemantica {
    //COMENTARIOS %%

    @Override
    public Token run() {
        Lexico.cursor--;
        Main.listaErrores.add("Error lexico. Se descarto un '%' en la linea " + Lexico.linea);
        return null;
    }
}