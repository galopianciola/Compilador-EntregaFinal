package main;

import java.util.Hashtable;

public class TablaSimbolos {
    private static Hashtable<String, Integer> tSimbolos;


    public TablaSimbolos() {
        tSimbolos = new Hashtable<String, Integer>();
    }

    public void agregarSimbolo(String lexema, Integer id) {
        if (tSimbolos.containsKey(lexema))
            System.out.println("El identificador " + lexema + " ya existe");
        else
            tSimbolos.put(lexema, id);
    }

    public void modificarSimbolo(String lexemaviejo, String lexemaNuevo) {
        tSimbolos.remove(lexemaviejo);
        tSimbolos.put(lexemaNuevo, Lexico.CTE_DOUBLE);
    }

    public void eliminarSimbolo(String lexema) {
        tSimbolos.remove(lexema);
    }

    public Integer getId(String s) {
        if (tSimbolos.containsKey(s))
            return tSimbolos.get(s);
        else
            return null;
    }

    public boolean existeLexema(String s) {
        return (tSimbolos.containsKey(s));
    }
}
