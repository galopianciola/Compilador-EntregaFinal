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

    public DatosTabla getDatosTabla(String lexema){
        if(tSimbolos.containsKey(lexema))
            return tSimbolos.get(lexema);
        return null;
    }

    public void agregarSimbolo(String lexema, Integer id, String tipo) {
        if (!tSimbolos.containsKey(lexema)) {
            DatosTabla dt = new DatosTabla();
            dt.setId(id);
            dt.setTipo(tipo);
            tSimbolos.put(lexema, dt);
        } else {
            // System.out.println("El identificador " + lexema + " ya existe");
        }
    }

    public void agregarSimbolo(String lexema, Integer id, String tipo, String uso) {
        if (!tSimbolos.containsKey(lexema)) {
            DatosTabla dt = new DatosTabla();
            dt.setId(id);
            dt.setTipo(tipo);
            dt.setUso(uso);
            tSimbolos.put(lexema, dt);
        } else {
            // System.out.println("El identificador " + lexema + " ya existe");
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
            if(lexema.contains("@") || (!lexema.contains("@") && tSimbolos.get(lexema).getId() != 257) || lexema.contains("var"))
                System.out.println("Lexema: " + lexema + ", id: " + tSimbolos.get(lexema).getId() +
                        ", tipo: " + tSimbolos.get(lexema).getTipo() + ", uso: " + tSimbolos.get(lexema).getUso()
                        + ", refencia" + tSimbolos.get(lexema).isParametroRef());
            else
                tSimbolos.remove(lexema);
        }
    }

    public void reemplazarLexema(String lexema, String nuevoLexema){
        DatosTabla dt = tSimbolos.get(lexema);
        tSimbolos.remove(lexema);
        tSimbolos.put(nuevoLexema, dt);
    }

    public void setDatosTabla( String lexema, DatosTabla dt){
        tSimbolos.replace(lexema, dt);
    }

    public String verificarAmbito(String ide, String ambito){
        String aux = ide + "@" + ambito;
        // Verificar ambito variables
        while(!aux.equals(ide)){
            if(tSimbolos.containsKey(aux)){
                return aux;
            }
            aux = aux.substring(0,aux.lastIndexOf("@"));
        }
        // Verificar ambito parametros
        String[] proc = ambito.split("@");
        for(int i = 1; i<proc.length; i++){
            aux = ide + "@" +proc[i];
            if(tSimbolos.containsKey(aux) && tSimbolos.get(aux).getUso().equals("nombreParametro")){
                return aux;
            }
        }
        return null;
    }

    public Enumeration getKeys(){
        return tSimbolos.keys();
    }

}
