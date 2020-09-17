package AccionesSemanticas;
import main.*;

public class AccionSemantica11 extends AccionSemantica {
    @Override
    public void run() {
        int nro = Integer.parseInt(buffer);

        if ((nro > 0) && (nro < (2^16 - 1))){
            /*Alta en la TS
              Devolver CTE + Punt TS.
            */
        }

    }
}
