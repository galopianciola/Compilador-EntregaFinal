package AccionesSemanticas;

import main.*;

public class AccionSemantica16 extends AccionSemantica {
    @Override
    public Token run() {
        buffer = buffer + Lexico.caracter;
        //Main.tSimbolos.agregarSimbolo(buffer, Lexico.IDE); // todo: por qué dos string??
        return new Token(Lexico.CADENA, buffer);
        //Alta en la TS
        //Devolver ID + Puntero TS
    }


}