package CodigoInt;

import java.util.ArrayList;

public class AdmTercetos {
    private ArrayList<Terceto> tercetos = new ArrayList<Terceto>();

    public AdmTercetos(){}

    public void agregarTerceto(Terceto t){
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }

    public void printTercetos(){
        for (Terceto t: this.tercetos){
            System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
        }
    }
}
