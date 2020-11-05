package CodigoInt;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Enumeration;
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
        procedimientos.put(proc, tercetos.size()-1);
    }

    public void printTercetos(){
        for (int i = 0; i < tercetos.size(); i++){
            Terceto t = tercetos.get(i);
            if(t.getOperador() == "PROC"){
                while(t.getOperador() != "FinProc"){
                    i++;
                    t = tercetos.get(i);
                }
                i++;
                //t = tercetos.get(i);
            } else
                if(t.getOperador() == "INV"){
                    String procInvocado = t.getOp1();
                    System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
                    int inicioProc = procedimientos.get(t.getOp1());
                    int finProc = this.buscarFinProc(t.getOp1());
                    System.out.println("inicio" + inicioProc + " fin "+finProc);
                    Terceto t1 = tercetos.get(inicioProc);
                    for(int index = inicioProc; index <= finProc; index++){
                        t1 = tercetos.get(index);
                        System.out.println(t1.getNumero() + ". (" + t1.getOperador() + ", " + t1.getOp1() + ", " + t1.getOp2() + ")");
                    }
                    //System.out.println(t1.getNumero() + ". (" + t1.getOperador() + ", " + t1.getOp1() + ", " + t1.getOp2() + ")");
                    i++;
                } else
                        System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
        }
    }

    public void printCodigoIntermedio(){
        for(Terceto t: tercetos)
            System.out.println(t.getNumero() + ". (" + t.getOperador()+", "+t.getOp1()+ ", "+t.getOp2()+")");
    }

    public void printProcedimientos() {
        System.out.println("\nProcedimientos:");
        System.out.println(procedimientos);
    }

    public int buscarFinProc(String procedimiento){
        for(Terceto t : tercetos){
            if(t.getOperador() == "FinProc" && t.getOp1() == procedimiento){
                System.out.println("entro if");
                return t.getNumero();}
        }
        return 0;
    }
}
