package CodigoInt;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class AdmTercetos {
    private ArrayList<Terceto> tercetos = new ArrayList<Terceto>();
    private ArrayList<Integer> pila = new ArrayList<>();
    private Hashtable<String, Integer> procedimientos = new Hashtable<String, Integer>();
    private ArrayList<ArrayList<Terceto>> codigoIntermedio = new ArrayList<>(5);

    public AdmTercetos() {
    }

    public void agregarTerceto(Terceto t) {
        t.setNumero(tercetos.size());
        tercetos.add(t);
    }

    public int cantTercetos() {
        return tercetos.size();
    }

    public void apilar(int nroTerceto) {
        pila.add(nroTerceto);
    }

    public void desapilar() {
        int tercetoIncompleto = pila.get(pila.size() - 1);
        pila.remove(pila.size() - 1);
        Terceto nuevoTerceto = tercetos.get(tercetoIncompleto);
        if (nuevoTerceto.getOperador() == "BF")
            nuevoTerceto.setOp2(Integer.toString(tercetos.size()));
        else
            nuevoTerceto.setOp1(Integer.toString(tercetos.size()));
        tercetos.set(tercetoIncompleto, nuevoTerceto);
    }

    public void desapilarFor() {
        int nroTerceto = pila.get(pila.size() - 1);
        pila.remove(pila.size() - 1);
        Terceto nuevoTerceto = tercetos.get(tercetos.size() - 1);
        nuevoTerceto.setOp1(Integer.toString(nroTerceto));
        tercetos.set(tercetos.size() - 1, nuevoTerceto);
    }

    public void agregarProcedimiento(String proc) {
        procedimientos.put(proc, tercetos.size() - 1);
    }

    public void generarCodigoIntermedio(int inicio, int finalProc, String proc, int index) {
        ArrayList<Terceto> aux = new ArrayList<>();
        ArrayList<String> invocados = new ArrayList<>();
        codigoIntermedio.add(index, new ArrayList<>());
        for (int i = inicio; i <= finalProc; i++) {
            Terceto t = tercetos.get(i);
            if (t.getOperador().equals("INV") && !invocados.contains(t.getOp1())) {
                String procInvocado = t.getOp1();
                generarCodigoIntermedio(procedimientos.get(procInvocado), this.buscarFinProc(procInvocado), procInvocado, index + 1);
                invocados.add(procInvocado);
            }
            while ((t.getOperador().equals("ComienzaProc") && !t.getOp1().equals(proc)) && (i <= finalProc)) {
                i = this.buscarFinProc(t.getOp1()) + 1;
                if (i <= finalProc)
                    t = tercetos.get(i);
            }
            if (i <= finalProc) {
                aux.add(t);
            }
        }
        codigoIntermedio.set(index, aux);
    }

    public void prinTercetos(){
        for(Terceto t : tercetos)
            System.out.println(t.getNumero() + ". (" + t.getOperador() + ", " + t.getOp1() + ", " + t.getOp2() + ")");
    }

    public void generarCodigoIntermedio(){
        this.generarCodigoIntermedio(0, tercetos.size() - 1, "main", 0);
}

    public void printCodigoIntermedio() {
        for (ArrayList<Terceto> a : codigoIntermedio) {
            for (Terceto t : a) {
                System.out.println(t.getNumero() + ". (" + t.getOperador() + ", " + t.getOp1() + ", " + t.getOp2() + ")");
            }
        }
    }

    public ArrayList<ArrayList<Terceto>> getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public int buscarFinProc(String procedimiento) {
        for (Terceto t : tercetos) {
            if (t.getOperador().equals("FinProc") && t.getOp1().equals(procedimiento)) {
                return t.getNumero();
            }
        }
        return 0;
    }

    public Terceto getTerceto(int nroTerceto) {
        return tercetos.get(nroTerceto);
    }
}
