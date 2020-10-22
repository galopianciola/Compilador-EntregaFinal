package main;

import com.sun.jmx.snmp.Enumerated;
import java.util.Enumeration;
import java.util.Hashtable;

public class TablaSimbolos {
    private static Hashtable<String, DatosTabla> tSimbolos;


    public TablaSimbolos() {
        tSimbolos = new Hashtable<String, DatosTabla>();
    }

    public void agregarSimbolo(String lexema, Integer id) {
        if (tSimbolos.containsKey(lexema))
            System.out.println("El identificador " + lexema + " ya existe");
        else {
            DatosTabla dt = new DatosTabla();
            dt.setId(id);
            tSimbolos.put(lexema, dt);
        }
    }

    public void agregarSimbolo(String lexema, Integer id, String tipo) {
        if (tSimbolos.containsKey(lexema))
            System.out.println("El identificador " + lexema + " ya existe");
        else {
            DatosTabla dt = new DatosTabla();
            dt.setId(id);
            dt.setTipo(tipo);
            tSimbolos.put(lexema, dt);
        }
    }

    public void modificarSimbolo(String lexemaviejo, String lexemaNuevo) {
        tSimbolos.remove(lexemaviejo);
        DatosTabla dt = new DatosTabla();
        dt.setId(Lexico.CTE_DOUBLE);
        dt.setTipo("DOUBLE");
        tSimbolos.put(lexemaNuevo, dt);
    }

    public void eliminarSimbolo(String lexema) {
        tSimbolos.remove(lexema);
    }

    public Integer getId(String s) {
        if (tSimbolos.containsKey(s))
            return tSimbolos.get(s).getId();
        else
            return null;
    }

    public boolean existeLexema(String s) {
        return (tSimbolos.containsKey(s));
    }

    public void printTablaSimbolos() {
        Enumeration iterador = tSimbolos.keys();
        while (iterador.hasMoreElements()) {
            String lexema = (String) iterador.nextElement();
            System.out.println("Lexema: " + lexema + ", id: " + tSimbolos.get(lexema).getId() +
                    ", tipo: " + tSimbolos.get(lexema).getTipo() + ", uso: " + tSimbolos.get(lexema).getUso());
        }
    }
}
