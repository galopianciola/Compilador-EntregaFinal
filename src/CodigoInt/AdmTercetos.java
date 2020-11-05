package CodigoInt;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Hashtable;

public class AdmTercetos {
    private ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
    private ArrayList<Integer> pila = new ArrayList<>();
    private Hashtable<String, Integer> procedimientos = new Hashtable<String, Integer>();

    public AdmTercetos(){}

    public void agregarTerceto(Terceto t){
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }

    public void apilar(int nroTerceto){
        pila.add(nroTerceto);
    }

    public void desapilar(){
        int tercetoIncompleto = pila.get(pila.size()-1);
        pila.remove(pila.size()-1);
        Terceto nuevoTerceto = tercetos.get(tercetoIncompleto);
        if(nuevoTerceto.getOperador() == "BF")
            nuevoTerceto.setOp2(Integer.toString(tercetos.size()));
        else
            nuevoTerceto.setOp1(Integer.toString(tercetos.size()));
        tercetos.set(tercetoIncompleto, nuevoTerceto);
    }

    public void desapilarFor(){
        int nroTerceto = pila.get(pila.size()-1);
        pila.remove(pila.size()-1);
        Terceto nuevoTerceto = tercetos.get(tercetos.size()-1);
        nuevoTerceto.setOp1(Integer.toString(nroTerceto));
        tercetos.set(tercetos.size()-1, nuevoTerceto);
    }

    public void agregarProcedimiento(String proc){
        procedimientos.put(proc, tercetos.size());
    }

    public void printTercetos(){
        for (Terceto t: this.tercetos){
            System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
        }
    }
}
