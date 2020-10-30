package CodigoInt;

import java.util.ArrayList;

public class AdmTercetos {
    private ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
    private ArrayList<Integer> pila = new ArrayList<>();

    public AdmTercetos(){}

    public void agregarTerceto(Terceto t){
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }

    public void apilar(int nroTerceto){
        System.out.println("apilo "+pila.size());
        pila.add(nroTerceto);
    }

    public void desapilar(){
        System.out.println("desapilo " + pila.size());
        int tercetoIncompleto = pila.get(pila.size()-1);
        pila.remove(pila.size()-1);
        Terceto nuevoTerceto = tercetos.get(tercetoIncompleto);
        if(nuevoTerceto.getOperador() == "BF")
            nuevoTerceto.setOp2(Integer.toString(tercetos.size()+1));
        else
            nuevoTerceto.setOp1(Integer.toString(tercetos.size()));
        tercetos.set(tercetoIncompleto, nuevoTerceto);
    }

    public void printTercetos(){
        for (Terceto t: this.tercetos){
            System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
        }
    }
}
