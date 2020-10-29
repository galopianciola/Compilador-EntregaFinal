package CodigoInt;

import java.util.ArrayList;

public class AdmTercetos {
    private ArrayList<Terceto> tercetos = new ArrayList<Terceto>();

    public AdmTercetos(){}

    public void agregarTerceto(Terceto t){
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }
}
