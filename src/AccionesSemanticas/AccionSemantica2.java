package AccionesSemanticas;
import main.*;


public class AccionSemantica2 extends AccionSemantica {

    @Override
    public void run() {
        buffer = buffer + Lexico.caracter;
    }
}
